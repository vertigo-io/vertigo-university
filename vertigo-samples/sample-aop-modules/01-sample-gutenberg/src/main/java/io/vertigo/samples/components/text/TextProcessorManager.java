package io.vertigo.samples.components.text;

import java.nio.file.Path;

import io.vertigo.core.component.Manager;

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
