package io.mars.basemanagement.services.job;

import javax.inject.Inject;

import io.mars.basemanagement.dao.JobDAO;
import io.mars.basemanagement.domain.Job;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class JobServicesImpl implements Component {

	@Inject
	private JobDAO jobDAO;

	public Job get(final Long jobId) {
		return jobDAO.get(jobId);
	}

	public void save(final Job job) {
		jobDAO.save(job);
	}

	public DtList<Job> getJobs(final DtListState dtListState) {
		return jobDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
