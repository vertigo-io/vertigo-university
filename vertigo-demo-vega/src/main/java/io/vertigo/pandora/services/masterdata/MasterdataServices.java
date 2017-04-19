package io.vertigo.pandora.services.masterdata;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Component;
import io.vertigo.pandora.domain.masterdata.Gender;

public interface MasterdataServices extends Component {

	DtList<Gender> getGenderList();
	
}
