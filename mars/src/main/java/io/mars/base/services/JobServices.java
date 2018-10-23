package io.mars.base.services;

import io.mars.base.domain.Job;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface JobServices extends Component {

	DtList<Job> getJobs(DtListState dtListState);

	void save(Job job);

	Job get(Long jobId);
}
