/**
 *
 */
package io.mars.boot;

import javax.inject.Inject;

import io.mars.basemanagement.domain.BaseType;
import io.mars.basemanagement.domain.Tag;
import io.mars.catalog.domain.EquipmentType;
import io.mars.maintenance.domain.WorkOrderStatus;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtStereotype;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;

/**
 * Init masterdata list.
 * @author jmforhan
 */
public class MasterDataInitializer implements ComponentInitializer {

	private static final int CACHE_DURATION_LONG = 3600;
	private static final int CACHE_DURATION_SHORT = 600;
	private static final String ACTIF_CODE = "ACTIF";
	private static final String IS_ACTIVE = "IS_ACTIVE";
	private static final String ALL_CODE = null;

	@Inject
	private StoreManager storeManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		registerAllMasterData(storeManager);
	}

	private static void registerAllMasterData(final StoreManager storeManager) {
		registerMasterData(storeManager, BaseType.class);
		registerMasterData(storeManager, EquipmentType.class);
		registerMasterData(storeManager, WorkOrderStatus.class);
		registerMasterData(storeManager, Tag.class);
	}

	private static <O extends DtObject> void registerMasterData(final StoreManager storeManager, final Class<O> dtObjectClass) {
		registerMasterData(storeManager, dtObjectClass, null, true, true);
	}

	private static <O extends DtObject> void registerMasterData(final StoreManager storeManager, final Class<O> dtObjectClass,
			final Integer duration, final boolean reloadItemByList, final boolean serializeItem) {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(dtObjectClass);
		// Si la durée dans le cache n'est pas précisé, on se base sur le type de la clé primaire pour déterminer la durée
		final int cacheDuration;
		if (duration == null) {
			if (dtDefinition.getStereotype() == DtStereotype.StaticMasterData) {
				cacheDuration = CACHE_DURATION_LONG;
			} else {
				cacheDuration = CACHE_DURATION_SHORT;
			}
		} else {
			cacheDuration = duration;
		}
		storeManager.getDataStoreConfig().registerCacheable(dtDefinition, cacheDuration, reloadItemByList, serializeItem);
		// on enregistre le filtre actif
		final DtListURIForMasterData uriActif = new DtListURIForMasterData(dtDefinition, ACTIF_CODE);
		if (dtDefinition.contains(IS_ACTIVE)) {
			storeManager.getMasterDataConfig().register(uriActif, IS_ACTIVE, Boolean.TRUE);
		} else {
			storeManager.getMasterDataConfig().register(uriActif);
		}
		// On enregistre la liste globale
		final DtListURIForMasterData uri = new DtListURIForMasterData(dtDefinition, ALL_CODE);
		storeManager.getMasterDataConfig().register(uri);
	}

}
