package io.mars.jobs.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.jobs.services.JobServices;
import io.vertigo.orchestra.domain.DtDefinitions.OProcessUiFields;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/jobs")
public class JobListController extends AbstractVSpringMvcController {

	private static final ViewContextKey<OProcessUi> jobs = ViewContextKey.of("jobs");

	@Inject
	private JobServices jobServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		// We make the call with the proper week dates
		viewContext.publishDtList(jobs, OProcessUiFields.NAME, jobServices.getProcessDefinitions());
	}

}
