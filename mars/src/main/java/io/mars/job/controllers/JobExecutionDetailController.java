package io.mars.job.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.job.services.JobServices;
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

	private static final ViewContextKey<OProcessExecutionUi> jobExecution = ViewContextKey.of("jobExecution");
	private static final ViewContextKey<OActivityExecutionUi> activityExecutions = ViewContextKey.of("activityExecutions");

	@GetMapping("/{preId}")
	public void initContext(final ViewContext viewContext, @PathVariable("preId") final Long preId) {
		viewContext.publishDto(jobExecution, jobServices.getProcessExecution(preId));
		viewContext.publishDtList(activityExecutions, OActivityExecutionUiFields.aceId, jobServices.getActivityExecutionsByProcessExecution(preId));
		//---
		toModeEdit();
	}

}
