package io.mars.jobs.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.jobs.services.JobServices;
import io.vertigo.orchestra.domain.DtDefinitions.OActivityExecutionUiFields;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OProcessExecutionUi;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/jobExecutionDetail")
public class JobExecutionDetailController extends AbstractVSpringMvcController {

	@Inject
	private JobServices jobServices;

	private final ViewContextKey<OProcessExecutionUi> jobExecution = ViewContextKey.of("jobExecution");
	private final ViewContextKey<OActivityExecutionUi> activityExecutions = ViewContextKey.of("activityExecutions");

	@GetMapping("/{preId}")
	public void initContext(final ViewContext viewContext, @PathVariable("preId") final Long preId) {
		viewContext.publishDto(jobExecution, jobServices.getProcessExecution(preId));
		viewContext.publishDtList(activityExecutions, OActivityExecutionUiFields.ACE_ID, jobServices.getActivityExecutionsByProcessExecution(preId));
		//---
		toModeEdit();
	}

}
