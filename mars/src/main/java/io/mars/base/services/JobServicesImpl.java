package io.mars.base.services;

import javax.inject.Inject;

import io.mars.base.dao.JobDAO;
import io.mars.base.domain.Job;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class JobServicesImpl implements JobServices {

	@Inject
	private JobDAO jobDAO;

	@Override
	public Job get(final Long jobId) {
		return jobDAO.get(jobId);
	}

	@Override
	public void save(final Job job) {
		jobDAO.save(job);
	}

	@Override
	public DtList<Job> getJobs(final DtListState dtListState) {
		return jobDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
