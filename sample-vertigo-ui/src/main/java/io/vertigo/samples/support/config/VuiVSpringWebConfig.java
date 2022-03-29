package io.vertigo.samples.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.LocaleResolver;

import io.vertigo.ui.impl.springmvc.config.VSpringWebConfig;
import io.vertigo.ui.impl.springmvc.config.VertigoLocaleResolver;

@ComponentScan("io.vertigo.samples.vui.controllers")
public class VuiVSpringWebConfig extends VSpringWebConfig {
	// nothing basic config is enough

	@Bean
	public LocaleResolver localeResolver() {
		return new VertigoLocaleResolver();
	}
}
