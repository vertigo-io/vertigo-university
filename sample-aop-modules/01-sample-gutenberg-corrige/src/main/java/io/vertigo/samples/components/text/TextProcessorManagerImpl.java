package io.vertigo.samples.components.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.core.lang.WrappedException;

/**
 *
 * @author dt
 *
 */
public class TextProcessorManagerImpl implements TextProcessorManager {

	@Override
	public long process(final Path text) {
		try {
			return Files.lines(text).count();
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}

	}

}
