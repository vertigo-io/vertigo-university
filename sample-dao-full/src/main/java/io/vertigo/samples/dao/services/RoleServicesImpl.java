package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.criteria.FilterCriteria;
import io.vertigo.dynamo.store.criteria.FilterCriteriaBuilder;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.dao.MyRoleDAO;
import io.vertigo.samples.dao.domain.MyActor;
import io.vertigo.samples.dao.domain.MyMovie;
import io.vertigo.samples.dao.domain.MyRole;

@Transactional
public class RoleServicesImpl implements RoleServices {

	@Inject
	private MyRoleDAO myRoleDAO;
	@Inject
	private MyMovieDAO myMovieDAO;

	@Override
	public DtList<MyRole> getRolesByMovie1(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		final MyMovie movie = myMovieDAO.get(movId);
		return movie.getRoleList();
	}

	@Override
	public DtList<MyRole> getRolesByMovie2(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		final FilterCriteria<MyRole> criteria = new FilterCriteriaBuilder<MyRole>().addFilter("MOV_ID", movId).build();
		return myRoleDAO.findAll(criteria, 500);
	}

	@Override
	public void addActorToMovie(final MyMovie myMovie, final MyActor actor, final String roleName) {
		Assertion.checkNotNull(myMovie);
		Assertion.checkNotNull(actor);
		Assertion.checkArgNotEmpty(roleName);
		// ---
		final MyRole newRole = new MyRole();
		newRole.setMovId(myMovie.getMovId());
		newRole.setActId(actor.getActId());
		newRole.setAsCharacter(roleName);
		myRoleDAO.save(newRole);

	}

	@Override
	public DtList<MyRole> getRolesInMovies(final DtList<MyMovie> movies) {
		return myRoleDAO.getRolesInMovies(movies);
	}

}
