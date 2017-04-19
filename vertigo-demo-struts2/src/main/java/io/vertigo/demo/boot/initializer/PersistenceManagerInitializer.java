package io.vertigo.demo.boot.initializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.commons.cache.CacheManager;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.demo.domain.referentiel.Famille;
import io.vertigo.demo.domain.tutorial.Commune;
import io.vertigo.demo.domain.tutorial.TutoObjectEtat;
import io.vertigo.demo.domain.tutorial.TutoObjectType;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtField;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;

/**
 * Initialisation du manager des masterdatas.
 * @author pchretien
 * @version $Id: PersistenceManagerInitializer.java,v 1.6 2014/08/04 16:57:50 npiedeloup Exp $
 */
public final class PersistenceManagerInitializer implements ComponentInitializer {
	private static final int CACHE_DURATION_LONG = 3600;
	private static final int CACHE_DURATION_SHORT = 600;
	private static final String ALL_DATA_CODE = null;
	private static final String IS_ACTIVE = "IS_ACTIVE";
	private static final String ACTIVE_DATA_CODE = "ACTIVE";

	/**
	 * Map static.
	 */
	private static final Map<Class<? extends DtObject>, Set<String>> MDL_MAP = new HashMap<>();

	@Inject
	private StoreManager persistenceManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		registerMasterData(persistenceManager, Famille.class);
		//Liste de référence des communes-CP
		registerMasterData(persistenceManager, Commune.class, CACHE_DURATION_LONG, true);

		// Enregistrement des listes de référence
		registerMasterData(persistenceManager, TutoObjectType.class, CACHE_DURATION_LONG, false);
		registerMasterData(persistenceManager, TutoObjectEtat.class, CACHE_DURATION_LONG, false);
	}

	private static <O extends DtObject> void registerMasterData(final StoreManager storeManager, final Class<O> dtObjectClass) {
		registerMasterData(storeManager, dtObjectClass, null, false);
	}

	private static <O extends DtObject> void registerMasterData(final StoreManager storeManager, final Class<O> dtObjectClass, final Integer duration, final boolean isBigList) {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(dtObjectClass);
		memorizeMdl(dtObjectClass, dtDefinition.getName());
		// Si la durée dans le cache n'est pas précisé, on se base sur le type de la clé primaire
		// pour déterminer la
		// durée
		final int cacheDuration;
		if (duration == null) {
			final DtField primaryKey = dtDefinition.getIdField().get();
			if (primaryKey.getDomain().getDataType() == DataType.String) {
				cacheDuration = CACHE_DURATION_LONG;
			} else {
				cacheDuration = CACHE_DURATION_SHORT;
			}
		} else {
			cacheDuration = duration;
		}
		storeManager.getDataStoreConfig().registerCacheable(dtDefinition, cacheDuration, !isBigList, !isBigList);
		// on enregistre le filtre actif
		final DtListURIForMasterData uriActif = new DtListURIForMasterData(dtDefinition, ACTIVE_DATA_CODE);
		if (dtDefinition.contains(IS_ACTIVE)) {
			storeManager.getMasterDataConfig().register(uriActif, IS_ACTIVE, Boolean.TRUE);
		} else {
			storeManager.getMasterDataConfig().register(uriActif);
		}
		// On enregistre la liste globale
		final DtListURIForMasterData uri = new DtListURIForMasterData(dtDefinition, ALL_DATA_CODE);
		storeManager.getMasterDataConfig().register(uri);
	}

	private static void memorizeMdl(final Class<? extends DtObject> dtObjectClass, final String cacheName) {
		Set<String> set = MDL_MAP.get(dtObjectClass);
		if (set == null) {
			set = new HashSet<>();
			MDL_MAP.put(dtObjectClass, set);
		}
		set.add(cacheName);
	}

	/**
	 * Vide le cache sur les listes de références.
	 */
	public static void clearMdlCache() {
		final CacheManager manager = Home.getApp().getComponentSpace().resolve(CacheManager.class);
		for (final Set<String> set : MDL_MAP.values()) {
			clearDtoCache(manager, set);
		}
	}

	private static void clearDtoCache(final CacheManager manager, final Set<String> set) {
		if (set != null) {
			final String debutContext = "DataCache:";
			for (final String mdl : set) {
				manager.clear(debutContext + mdl);
			}
		}
	}

	/**
	 * Vide le cache de façon spécifique sur une liste de référence.
	 *
	 * @param dtObjectClass classe de lal iste de référence
	 */
	public static void clearMdlCache(final Class<? extends DtObject> dtObjectClass) {
		final CacheManager manager = Home.getApp().getComponentSpace().resolve(CacheManager.class);
		final Set<String> set = MDL_MAP.get(dtObjectClass);
		clearDtoCache(manager, set);
	}

}
