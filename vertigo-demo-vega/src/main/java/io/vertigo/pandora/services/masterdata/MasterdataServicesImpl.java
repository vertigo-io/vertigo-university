package io.vertigo.pandora.services.masterdata;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.pandora.domain.masterdata.Gender;

public class MasterdataServicesImpl implements MasterdataServices {

	@Override
	public DtList<Gender> getGenderList() {
		final DtList<Gender> genderList = new DtList<>(Gender.class);

		final Gender male = new Gender();
		male.setCode("M");
		male.setLabel("Homme");
		male.setIsActive(true);
		genderList.add(male);

		final Gender female = new Gender();
		female.setCode("F");
		female.setLabel("Femme");
		female.setIsActive(true);
		genderList.add(female);

		return genderList;
	}

}
