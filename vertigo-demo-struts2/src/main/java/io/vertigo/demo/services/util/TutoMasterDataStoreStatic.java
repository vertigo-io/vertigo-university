package io.vertigo.demo.services.util;

import javax.inject.Inject;

import io.vertigo.demo.domain.tutorial.TutoObjectEtat;
import io.vertigo.demo.domain.tutorial.TutoObjectType;
import io.vertigo.dynamo.domain.model.DtList;

/**
 * MasterDataStore for the static lists.
 * @author npiedeloup
 * @version $Id: TutoMasterDataStoreStatic.java,v 1.3 2014/06/27 12:21:39 pchretien Exp $
 */
public final class TutoMasterDataStoreStatic extends AbstractStaticMDDataStorePlugin {
	private static final String[] STATIC_REF = { "Catheter (T)", "Catheter, angioplasty (T)", "Catheter, angioplasty, atherectomy (P)", "Catheter, angioplasty, atherectomy, ablative (P)", "Catheter, angioplasty, balloon dilatation (P)", "Catheter, cardiac (T)", "Catheter, cardiac, ablation (P)", "Catheter, cardiac, balloon (T)", "Catheter, cardiac, balloon, intra-aortic (P)", "Catheter, cardiac, balloon, pacing electrode (P)", "Catheter, cardiac, balloon, pulmonary artery (P)", };

	@Inject
	public TutoMasterDataStoreStatic() {
		super("static");
	}

	/**
	 * @return Liste des TutoObjectType.
	 */
	public TutoObjectType getTutoObjectEtatMDObject(final Long typId) {
		final TutoObjectType tutoObjectType = new TutoObjectType();
		tutoObjectType.setTypId(typId);
		final String code = String.valueOf((char) ('R' + typId / 10)) + typId % 10;
		tutoObjectType.setCode(code);
		tutoObjectType.setLibelle(getDataSpace() + "(+):Type" + typId);
		return tutoObjectType;
	}

	/**
	 * @return Liste des TutoObjectType.
	 */
	public DtList<TutoObjectType> getTutoObjectTypeMDList() {
		final DtList<TutoObjectType> valueList = new DtList<>(TutoObjectType.class);
		int seq = 0;
		for (int i = 0; i < 12; i++) {
			final TutoObjectType tutoObjectType = new TutoObjectType();
			tutoObjectType.setTypId(Long.valueOf(++seq));
			final String code = String.valueOf((char) ('R' + seq / 10)) + seq % 10;
			tutoObjectType.setCode(code);
			tutoObjectType.setLibelle(getDataSpace() + ":Type" + seq);
			valueList.add(tutoObjectType);
		}
		for (final String elem : STATIC_REF) {
			final TutoObjectType tutoObjectType = new TutoObjectType();
			tutoObjectType.setTypId(Long.valueOf(++seq));
			final String code = String.valueOf((char) ('S' + seq / 10)) + seq % 10;
			tutoObjectType.setCode(code);
			tutoObjectType.setLibelle(elem);
			valueList.add(tutoObjectType);
		}
		return valueList;
	}

	/**
	 * @return Liste des TutoObjectEtat.
	 */
	public DtList<TutoObjectEtat> getTutoObjectEtatMDList() {
		final DtList<TutoObjectEtat> valueList = new DtList<>(TutoObjectEtat.class);
		for (int seq = 0; seq < 12; seq++) {
			final TutoObjectEtat tutoObjectEtat = new TutoObjectEtat();
			tutoObjectEtat.setEtaId(Long.valueOf(seq));
			final String etat = String.valueOf((char) ('E' - seq / 10)) + seq % 10;
			tutoObjectEtat.setEtat(etat);
			tutoObjectEtat.setLibelle(getDataSpace() + ":Etat" + seq);
			valueList.add(tutoObjectEtat);
		}
		return valueList;
	}

	/*public DtList<TutoObjectType> getTutoType2MDList(final DtListURIForMasterData uri) throws KSystemException {
		final DtList<TutoObjectType> valueList = DtUtil.createDtList(AbstractTutoObjectType.DEFINITION_URN);
		for (long seq = 0; seq < 20; seq++) {
			final TutoObjectType tutoObjectType = new TutoObjectType();
			tutoObjectType.setTypId(seq);
			tutoObjectType.setCode("U" + seq % 10);
			tutoObjectType.setLibelle("Ref2:Type" + seq);
			valueList.add(tutoObjectType);
		}
		return valueList;
	}*/
}
