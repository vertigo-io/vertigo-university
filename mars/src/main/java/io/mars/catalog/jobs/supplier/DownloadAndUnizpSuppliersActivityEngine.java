package io.mars.catalog.jobs.supplier;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;

import io.mars.catalog.services.supplier.SupplierServices;
import io.vertigo.core.param.ParamManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;
import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

public class DownloadAndUnizpSuppliersActivityEngine extends AbstractActivityEngine {

	@Inject
	private SupplierServices supplierServices;

	@Inject
	private FileManager fileManager;
	@Inject
	private ParamManager paramManager;

	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		Assertion.checkState(workspace.containsKey("stockSireneUniteLegaleUrl"), "param stockSireneUniteLegaleUrl is required");
		//---
		final String url = workspace.getValue("stockSireneUniteLegaleUrl");
		final String rootDirectory = "file:///" + paramManager.getParam("orchestra.root.directory").getValueAsString() + "/IMPORT/SIRENE/";
		try {
			final VFile vFile = fileManager.createFile("StockUniteLegale_utf8.zip", "application/zip", new URL(url));
			try (final InputStream inputStream = vFile.createInputStream()) {
				final Path destPath = Paths.get(new URL(rootDirectory + "StockUniteLegale_utf8.zip").toURI());
				destPath.toFile().mkdirs();
				Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (final URISyntaxException | IOException e) {
				throw WrappedException.wrap(e);
			}
		} catch (final MalformedURLException e) {
			throw WrappedException.wrap(e);
		}
		try (final InputStream inputStream = java.nio.file.Files.newInputStream(Paths.get(new URL(rootDirectory + "StockUniteLegale_utf8.zip").toURI()))) {
			try (final ZipInputStream zis = new ZipInputStream(inputStream)) {
				zis.getNextEntry(); // only one entry
				final Path destFile = Paths.get(new URL(rootDirectory + "StockUniteLegale_utf8.csv").toURI());
				if (Files.exists(destFile)) { //replaced old file
					Files.delete(destFile);
				}
				try (BufferedOutputStream bos = new BufferedOutputStream(java.nio.file.Files.newOutputStream(destFile))) {
					final byte[] bytesIn = new byte[1024];
					int read = 0;
					while ((read = zis.read(bytesIn)) != -1) {
						bos.write(bytesIn, 0, read);
					}
				}
				zis.closeEntry();
			}
		} catch (final URISyntaxException | IOException e) {
			throw WrappedException.wrap(e);
		}
		supplierServices.cleanAll();
		workspace.setSuccess();
		return workspace;
	}

}
