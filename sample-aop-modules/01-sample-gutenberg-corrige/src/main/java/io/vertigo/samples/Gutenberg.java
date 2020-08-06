package io.vertigo.samples;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.samples.components.text.TextProcessorManager;
import io.vertigo.samples.config.SampleConfigBuilder;

/***
 * @author dt
 */
public class Gutenberg {

	public static void main(final String[] args) throws URISyntaxException {
		try (final AutoCloseableNode node = new AutoCloseableNode(new SampleConfigBuilder().build())) {

			final TextProcessorManager textProcessor = node.getComponentSpace().resolve(TextProcessorManager.class);

			final Path path = Paths.get(ClassLoader.getSystemResource("maupassant.txt").toURI());

			final long resultNbLignes = textProcessor.process(path);
			System.out.println("Nombre de lignes = " + resultNbLignes);

		}
	}

}
