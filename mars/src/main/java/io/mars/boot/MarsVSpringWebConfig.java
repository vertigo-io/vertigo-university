package io.mars.boot;

import org.springframework.context.annotation.ComponentScan;

import io.vertigo.ui.impl.springmvc.config.VSpringWebConfig;

@ComponentScan({
		"io.mars.commons.controllers",
		"io.mars.basemanagement.controllers.base",
		"io.mars.basemanagement.controllers.equipment",
		"io.mars.hr.controllers.login",
		"io.mars.hr.controllers.person",
		"io.mars.maintenance.controllers.ticket",
		"io.mars.jobs.controllers" })
public class MarsVSpringWebConfig extends VSpringWebConfig {
	// nothing basic config is enough
}
