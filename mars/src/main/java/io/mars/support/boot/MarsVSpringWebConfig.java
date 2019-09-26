package io.mars.support.boot;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;

import io.vertigo.app.Home;
import io.vertigo.core.param.Param;
import io.vertigo.core.param.ParamManager;
import io.vertigo.ui.impl.springmvc.config.VSpringWebConfig;

@ComponentScan({
		"io.mars.support.controllers",
		"io.mars.home.controllers",
		"io.mars.basemanagement.controllers.base",
		"io.mars.basemanagement.controllers.equipment",
		"io.mars.catalog.controllers.supplier",
		"io.mars.hr.controllers.login",
		"io.mars.hr.controllers.person",
		"io.mars.hr.controllers.mission",
		"io.mars.maintenance.controllers",
		"io.mars.maintenance.controllers.ticket",
		"io.mars.maintenance.controllers.workorder",
		"io.mars.job.controllers",
		"io.mars.opendata.controllers",
		"io.mars.command.controllers" })
public class MarsVSpringWebConfig extends VSpringWebConfig {
	// nothing basic config is enough

	@Override
	protected boolean isDevMode() {
		final ParamManager paramManager = Home.getApp().getComponentSpace().resolve(ParamManager.class);
		final Optional<Param> devModeOpt = paramManager.getOptionalParam("devMode");
		if (devModeOpt.isPresent()) {
			return devModeOpt.get().getValueAsBoolean();
		}
		return true;
	}
}
