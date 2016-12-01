package io.vertigo.samples.components.a_basics;

import java.nio.file.Path;

import io.vertigo.lang.Manager;

/**
 *
 * @author dt
 *
 */
public interface TextProcessorManager extends Manager {

	/**
	 * Process a text
	 * @param text
	 */
	public long process(Path text);
}
