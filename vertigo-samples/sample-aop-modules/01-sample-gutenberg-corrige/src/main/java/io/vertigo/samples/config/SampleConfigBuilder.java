package io.vertigo.samples.config;

import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.samples.components.scrabble.ScrabbleManager;
import io.vertigo.samples.components.scrabble.ScrabbleManagerImpl;
import io.vertigo.samples.components.text.TextProcessorManager;
import io.vertigo.samples.components.text.TextProcessorManagerImpl;

/**
 *
 * @author dt
 *
 */
public class SampleConfigBuilder {
	public NodeConfig build() {
		return NodeConfig.builder()
				.addModule(ModuleConfig.builder("gutenberg")
						.addComponent(TextProcessorManager.class, TextProcessorManagerImpl.class)
						.addComponent(ScrabbleManager.class, ScrabbleManagerImpl.class)
						.build())
				.build();
	}
}
