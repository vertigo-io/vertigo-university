package io.vertigo.samples.quarto;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.datastore.filestore.model.VFile;
import io.vertigo.datastore.filestore.util.FileUtil;
import io.vertigo.quarto.impl.services.publisher.PublisherDataUtil;
import io.vertigo.quarto.services.publisher.PublisherManager;
import io.vertigo.quarto.services.publisher.metamodel.PublisherDataDefinition;
import io.vertigo.quarto.services.publisher.model.PublisherData;
import io.vertigo.quarto.services.publisher.model.PublisherNode;
import io.vertigo.samples.quarto.config.SampleQuartoConfigBuilder;
import io.vertigo.samples.quarto.domain.Theme;
import io.vertigo.samples.quarto.services.ThemeProvider;

/***
 * Start the main method.
 *
 * Go find your document in D:\SampleTheme.docx
 * Modifiy the path in the save method if you want to generate the document in another place!
 *
 * @author mlaroche
 */
public class SampleQuarto {

	public static void main(final String[] args) {
		try (final AutoCloseableApp app = new AutoCloseableApp(SampleQuartoConfigBuilder.config())) {
			// nothing
			final ThemeProvider themeProvider = app.getComponentSpace().resolve(ThemeProvider.class);
			final PublisherManager publisherManager = app.getComponentSpace().resolve(PublisherManager.class);
			final ResourceManager resourceManager = app.getComponentSpace().resolve(ResourceManager.class);

			final Theme theme = themeProvider.getSampleTheme();

			final PublisherDataDefinition publisherDataDefinition = app.getDefinitionSpace().resolve("PuTheme", PublisherDataDefinition.class);
			final PublisherData publisherData = new PublisherData(publisherDataDefinition);

			final PublisherNode rootNode = publisherData.getRootNode();
			PublisherDataUtil.populateData(theme, rootNode);

			final URL modelURL = resourceManager.resolve("models/ThemeModel.docx");
			final VFile vFile = publisherManager.publish(theme.getName(), modelURL, publisherData);
			save(vFile);
		}
	}

	private static void save(final VFile result) {
		try {
			FileUtil.copy(result.createInputStream(), new File("D:/" + result.getFileName() + ".docx"));
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
	}
}
