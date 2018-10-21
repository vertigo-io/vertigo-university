package io.vertigo.demo.services.dynaobject;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURI;
import io.vertigo.dynamo.domain.model.DtListURIForCriteria;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.store.StoreManager;

/**
 * Service g�n�rique pour la gestion de la persistance des DtObjects.
 *
 * @author prahmoune
 * @version $Id: DynaObjectServiceImpl.java,v 1.5 2014/06/27 12:21:39 pchretien Exp $
 *
 */
@Named("dynaObjectService")
@Transactional
public final class DynaObjectServiceImpl implements DynaObjectService {

	@Inject
	private StoreManager storeManager;

	/** {@inheritDoc} */
	@Override
	public Entity load(final DtDefinition dtDefinition, final Serializable id) {
		final URI<Entity> uri = URI.of(dtDefinition, id);
		return storeManager.getDataStore().readOne(uri);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<? extends Entity> getList(final DtDefinition dtDefinition) {
		final DtListURI collectionURI = new DtListURIForCriteria<>(dtDefinition, null, null);
		return storeManager.getDataStore().findAll(collectionURI);
	}

	/** {@inheritDoc} */
	@Override
	public void save(final Entity dtObject) {
		storeManager.getDataStore().update(dtObject);
	}

	/** {@inheritDoc} */
	@Override
	public void delete(final DtDefinition dtDefinition, final Serializable id) {
		final URI<Entity> uri = URI.of(dtDefinition, id);
		storeManager.getDataStore().delete(uri);
	}

}
