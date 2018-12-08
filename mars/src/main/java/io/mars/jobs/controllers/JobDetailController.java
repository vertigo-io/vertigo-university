package io.mars.jobs.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.jobs.services.JobServices;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/job")
public class JobDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<OProcessUi> job = ViewContextKey.of("job");

	@Inject
	private JobServices jobServices;

	@GetMapping("/{jobName}")
	public void initContext(final ViewContext viewContext, @PathVariable("jobName") final String jobName) {
		viewContext.publishDto(job, jobServices.getProcessDefinition(jobName));
		toModeReadOnly();
	}

}
