package io.vertigo.samples.config;

import io.vertigo.app.config.AppConfig;
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
	public AppConfig build() {
		return AppConfig.builder()
				.addModule(ModuleConfig.builder("gutenberg")
						.addComponent(TextProcessorManager.class, TextProcessorManagerImpl.class)
						.addComponent(ScrabbleManager.class, ScrabbleManagerImpl.class)
						.build())
				.build();
	}
}
