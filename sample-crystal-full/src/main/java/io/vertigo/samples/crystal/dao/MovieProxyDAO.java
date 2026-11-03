package io.vertigo.samples.crystal.dao;

import io.vertigo.basics.task.TaskEngineSelect;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.node.component.Amplifier;
import io.vertigo.datamodel.task.proxy.TaskInput;
import io.vertigo.datamodel.task.proxy.TaskOutput;
import io.vertigo.datamodel.task.proxy.TaskProxyAnnotation;

@Transactional
public interface MovieProxyDAO extends Amplifier {

	@TaskProxyAnnotation(
			name = "TkMovieCountByCountry",
			request = "select count(*) from movie where cou_id = #couId#",
			taskEngineClass = TaskEngineSelect.class)
	@TaskOutput(smartType = "STyCount")
	int count(@TaskInput(name = "couId", smartType = "STyId") Long couId);

}
