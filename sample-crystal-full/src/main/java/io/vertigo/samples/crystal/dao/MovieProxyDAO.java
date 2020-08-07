package io.vertigo.samples.crystal.dao;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.node.component.Amplifier;
import io.vertigo.datamodel.task.proxy.TaskInput;
import io.vertigo.datamodel.task.proxy.TaskOutput;
import io.vertigo.datamodel.task.proxy.TaskProxyAnnotation;
import io.vertigo.dynamox.task.TaskEngineSelect;

@Transactional
public interface MovieProxyDAO extends Amplifier {

	@TaskProxyAnnotation(
			name = "TK_MOVIE_COUNT_BY_COUNTRY",
			request = "select count(*) from movie where cou_id = #COU_ID#",
			taskEngineClass = TaskEngineSelect.class)
	@TaskOutput(smartType = "DO_INTEGER")
	int count(
			@TaskInput(name = "COU_ID", smartType = "DO_ID") Long couId);

}
