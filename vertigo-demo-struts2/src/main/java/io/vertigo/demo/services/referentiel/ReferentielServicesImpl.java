package io.vertigo.demo.services.referentiel;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.demo.domain.referentiel.Famille;
import io.vertigo.demo.domain.referentiel.OuiNonChoice;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;

/**
 * services associés à la gestion des produits.
 *
 * @author cgodard
 * @version $Id: ReferentielServicesImpl.java,v 1.5 2014/06/27 12:21:39 pchretien Exp $
 */
@Named("referentielServices")
@Transactional
public class ReferentielServicesImpl implements ReferentielServices {

	private List<OuiNonChoice> ouiNonList;

	@Inject
	private StoreManager storeManager;

	/**
	 * Initialisation liste de ref statiques.
	 */
	@PostConstruct
	public void init() {
		initOuiNonList();
	}

	/** {@inheritDoc} */
	@Override
	public DtList<Famille> loadFamille() {
		final DtListURIForMasterData mdlUri = storeManager.getMasterDataConfig().getDtListURIForMasterData(DtObjectUtil.findDtDefinition(Famille.class));
		return storeManager.getDataStore().findAll(mdlUri);
	}

	/** {@inheritDoc} */
	@Override
	public Famille getFamille(final Long famId) {
		final UID<Famille> uri = UID.of(Famille.class, famId);
		return storeManager.getDataStore().<Famille> readOne(uri);
	}

	/** {@inheritDoc} */
	@Override
	public List<OuiNonChoice> getOuiNonList() {
		return ouiNonList;
	}

	private void initOuiNonList() {
		ouiNonList = new DtList<>(OuiNonChoice.class);
		ouiNonList.add(createOuiNonChoice(Boolean.TRUE, "Oui"));
		ouiNonList.add(createOuiNonChoice(Boolean.FALSE, "Non"));
	}

	private OuiNonChoice createOuiNonChoice(final Boolean key, final String libelle) {
		final OuiNonChoice dto = new OuiNonChoice();
		dto.setKey(key);
		dto.setLibelle(libelle);
		return dto;
	}

}
