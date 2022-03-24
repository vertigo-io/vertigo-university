package io.vertigo.samples.support.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.component.Component;
import io.vertigo.datastore.filestore.FileStoreManager;
import io.vertigo.datastore.filestore.definitions.FileInfoDefinition;
import io.vertigo.datastore.filestore.model.FileInfo;
import io.vertigo.datastore.filestore.model.FileInfoURI;
import io.vertigo.datastore.filestore.model.VFile;
import io.vertigo.samples.support.fileinfo.FileInfoTmp;

@Transactional
public class SampleFileServices implements Component {

	@Inject
	private FileStoreManager fileStoreManager;

	public FileInfo saveFileTmp(final VFile file) {
		//apply security check
		return fileStoreManager.create(new FileInfoTmp(file));
	}

	public FileInfo getFile(final FileInfoURI fileUri) {
		//no file store check (use by uploader component)
		return fileStoreManager.read(fileUri);
	}

	public VFile getFileTmp(final FileInfoURI fileTmpUri) {
		final FileInfoDefinition tmpFileInfoDefinition = FileInfoDefinition.findFileInfoDefinition(FileInfoTmp.class);
		Assertion.check().isTrue(tmpFileInfoDefinition.equals(fileTmpUri.getDefinition()), "Can't access this file storage."); //not too much infos for security purpose
		return fileStoreManager.read(fileTmpUri).getVFile();
	}

	public void deleteFileTmp(final FileInfoURI fileTmpUri) {
		final FileInfoDefinition tmpFileInfoDefinition = FileInfoDefinition.findFileInfoDefinition(FileInfoTmp.class);
		Assertion.check().isTrue(tmpFileInfoDefinition.equals(fileTmpUri.getDefinition()), "Can't access this file storage."); //not too much infos for security purpose
		fileStoreManager.delete(fileTmpUri);
	}

}
