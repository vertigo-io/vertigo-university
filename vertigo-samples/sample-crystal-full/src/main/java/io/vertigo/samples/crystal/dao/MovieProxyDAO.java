package io.vertigo.samples.crystal.dao;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.task.proxy.TaskAnnotation;
import io.vertigo.dynamo.task.proxy.TaskInput;
import io.vertigo.dynamo.task.proxy.TaskOutput;
import io.vertigo.dynamox.task.TaskEngineSelect;

@Transactional
public interface MovieProxyDAO extends Component {

	@TaskAnnotation(
			name = "TK_MOVIE_COUNT_BY_COUNTRY",
			request = "select count(*) from movie where cou_id = #COU_ID#",
			taskEngineClass = TaskEngineSelect.class)
	@TaskOutput(domain = "DO_INTEGER")
	int count(
			@TaskInput(name = "COU_ID", domain = "DO_ID") Long couId);

}
