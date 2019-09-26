package io.mars.job.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.job.services.JobServices;
import io.vertigo.orchestra.domain.DtDefinitions.OProcessExecutionUiFields;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OProcessExecutionUi;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/jobExecutions")
public class JobExecutionsController extends AbstractVSpringMvcController {

	private static final ViewContextKey<OProcessUi> jobKey = ViewContextKey.of("job");
	private static final ViewContextKey<OProcessExecutionUi> jobExecutions = ViewContextKey.of("jobExecutions");

	@Inject
	private JobServices jobServices;

	@GetMapping("/{jobName}")
	public void initContext(final ViewContext viewContext, @PathVariable("jobName") final String jobName) {
		viewContext.publishDto(jobKey, jobServices.getProcessDefinition(jobName));
		viewContext.publishDtList(jobExecutions, OProcessExecutionUiFields.preId, jobServices.getProcessExecutions(jobName, "", 250, 0));
	}

}
