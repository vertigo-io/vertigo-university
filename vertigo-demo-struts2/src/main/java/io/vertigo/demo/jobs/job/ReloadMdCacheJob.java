package io.vertigo.demo.jobs.job;

import java.util.Collections;
import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.demo.domain.DtDefinitions;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtField;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.orchestra.plugins.store.OWorkspace;
import io.vertigo.orchestra.services.run.JobEngine;

/**
 * Job de rechargement du cache des MasterDataList.
 * @author npiedeloup
 * @version $Id: ReloadMdCacheJob.java,v 1.3 2014/02/07 16:48:27 npiedeloup Exp $
 */
public class ReloadMdCacheJob implements JobEngine {
	private final Logger logger = LogManager.getLogger(getClass());
	@Inject
	private StoreManager storeManager;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private CollectionsManager collectionsManager;

	/** {@inheritDoc} */
	@Override
	public void execute(final OWorkspace workspace) {
		for (final Class<?> dtDefinitionClazz : new DtDefinitions()) {
			final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition((Class<? extends DtObject>) dtDefinitionClazz);
			if (storeManager.getMasterDataConfig().containsMasterData(dtDefinition)) {
				ensureInCache(dtDefinition);
			}
		}
	}

	private void ensureInCache(final DtDefinition dtDefinition) {
		final DtListURIForMasterData dtListURI = storeManager.getMasterDataConfig().getDtListURIForMasterData(dtDefinition);
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final Optional<DtField> displayField = dtDefinition.getDisplayField();
			if (displayField.isPresent()) {
				final DtList<Entity> dtList = storeManager.getDataStore().findAll(dtListURI); //On charge la liste
				final UnaryOperator<DtList<Entity>> function = collectionsManager.<Entity> createIndexDtListFunctionBuilder()
						.filter("test", 1, Collections.singletonList(displayField.get()))
						.build();
				function.apply(dtList); //On charge l'index
			} else {
				logger.warn("La liste de référence " + dtDefinition.getLocalName() + " ne possède pas de champs Display (à ajouter dans les KSP).");
			}
		}
	}

}
