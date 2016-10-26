package io.vertigo.samples.dao.sevices;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.MyActor;
import io.vertigo.samples.dao.domain.MyMovie;
import io.vertigo.samples.dao.domain.MyRole;

public interface RoleServices extends StoreServices {

	DtList<MyRole> getRolesByMovie1(Long movId);

	DtList<MyRole> getRolesByMovie2(Long movId);

	void addActorToMovie(MyMovie myMovie, MyActor actor, String roleName);

	DtList<MyRole> getRolesInMovies(DtList<MyMovie> movies);

}
