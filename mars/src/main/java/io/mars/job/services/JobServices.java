package io.mars.job.services;

import java.time.Instant;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.monitoring.dao.summary.SummaryPAO;
import io.vertigo.orchestra.monitoring.dao.uidefinitions.UidefinitionsPAO;
import io.vertigo.orchestra.monitoring.dao.uiexecutions.UiexecutionsPAO;
import io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OProcessExecutionUi;

@Transactional
public class JobServices implements Component {

	@Inject
	private UiexecutionsPAO uiexecutionsPAO;
	@Inject
	private UidefinitionsPAO uidefinitionsPAO;
	@Inject
	private SummaryPAO summaryPAO;

	public DtList<OProcessExecutionUi> getProcessExecutions(final String processName, final String status, final Integer limit, final Integer offset) {
		Assertion.checkArgNotEmpty(processName);
		//---
		return uiexecutionsPAO.getExecutionsByProcessName(processName, status, limit, offset);
	}

	public DtList<OExecutionSummary> getSummariesByDate(final Instant minDate, final Instant maxDate, final Optional<String> status) {
		return summaryPAO.getExecutionSummariesByDate(minDate, maxDate, status.orElse(null));
	}

	public OExecutionSummary getSummaryByDate(final String processName, final Instant minDate, final Instant maxDate) {
		Assertion.checkArgNotEmpty(processName);
		//---
		return summaryPAO.getExecutionSummaryByDateAndName(minDate, maxDate, processName);
	}

	public OProcessExecutionUi getProcessExecution(final Long preId) {
		return uiexecutionsPAO.getExecutionByPreId(preId);
	}

	public DtList<OActivityExecutionUi> getActivityExecutionsByProcessExecution(final Long preId) {
		return uiexecutionsPAO.getActivitiesByPreId(preId);
	}

	public OActivityExecutionUi getActivityExecution(final Long aceId) {
		return uiexecutionsPAO.getActivitiyByAceId(aceId);
	}

	public DtList<OProcessUi> getProcessDefinitions() {
		return uidefinitionsPAO.searchProcessByLabel("%%");
	}

	public OProcessUi getProcessDefinition(final String jobName) {
		return uidefinitionsPAO.getProcessByName(jobName);
	}

}
