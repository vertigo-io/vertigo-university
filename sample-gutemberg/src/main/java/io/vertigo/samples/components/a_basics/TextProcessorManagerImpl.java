package io.vertigo.samples.components.a_basics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.lang.WrappedException;

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
			throw new WrappedException(e);
		}

	}

}
