package io.vertigo.samples.uihello.support.config;

import org.springframework.context.annotation.ComponentScan;

import io.vertigo.ui.impl.springmvc.config.VSpringWebConfig;

@ComponentScan("io.vertigo.samples.uihello.controllers")
public class SampleUiHelloVSpringWebConfig extends VSpringWebConfig {
	// rien : la config de base suffit
}
