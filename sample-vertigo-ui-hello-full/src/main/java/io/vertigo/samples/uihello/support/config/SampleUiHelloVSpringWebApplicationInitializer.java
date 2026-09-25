package io.vertigo.samples.uihello.support.config;

import io.vertigo.ui.impl.springmvc.config.AbstractVSpringMvcWebApplicationInitializer;

public class SampleUiHelloVSpringWebApplicationInitializer extends AbstractVSpringMvcWebApplicationInitializer {

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SampleUiHelloVSpringWebConfig.class };
	}
}
