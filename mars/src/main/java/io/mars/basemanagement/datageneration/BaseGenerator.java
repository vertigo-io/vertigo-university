package io.mars.basemanagement.datageneration;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.domain.Base;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;

@Transactional
public class BaseGenerator implements Component {

	@Inject
	private BasemanagementPAO basemanagementPAO;

	@Inject
	private BaseDAO baseDAO;

	public void generateInitialBases() {
		final List<String> nameFirstPartDictionnary1 = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta");
		final List<String> nameSecondPartDictionnary2 = Arrays.asList("Centauri", "Aldebaran", "Pisces", "Cygnus", "Pegasus", "Dragon", "Andromeda");
		final List<String> sampleTags = Arrays.asList("#mountain", "#sea", "#historic", "#cold", "#first", "#nasa", "#experimental");

		final List<Base> baseList = new FakeBaseListBuilder()
				.withMaxValues(50)
				.withGeosectorIds(basemanagementPAO.selectGeosectorId())
				.withNameDictionnaries(nameFirstPartDictionnary1, nameSecondPartDictionnary2)
				.withTagsDictionnary(sampleTags)
				.build();

		for (final Base base : baseList) {
			baseDAO.create(base);
		}

	}

}
