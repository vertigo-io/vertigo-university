package io.vertigo.mars.boot;

import org.springframework.context.annotation.ComponentScan;

import io.vertigo.ui.impl.springmvc.config.VSpringWebConfig;

@ComponentScan("io.vertigo.mars.controller")
public class MarsVSpringWebConfig extends VSpringWebConfig {
	// nothing basic config is enough
}
