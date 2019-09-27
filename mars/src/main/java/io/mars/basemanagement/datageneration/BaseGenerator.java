package io.mars.basemanagement.datageneration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.dao.PictureDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Picture;
import io.mars.fileinfo.FileInfoStd;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;

@Transactional
public class BaseGenerator implements Component {

	@Inject
	private BasemanagementPAO basemanagementPAO;

	@Inject
	private BaseDAO baseDAO;
	@Inject
	private PictureDAO pictureDAO;

	@Inject
	private FileManager fileManager;
	@Inject
	private StoreManager storeManager;

	public List<Base> generateInitialBases() {
		final List<String> geoLocations = Collections.singletonList("{ \"lon\": 2.333333 , \"lat\" : 48.866667 } "); //paris by default for everybody

		final List<String> nameFirstPartDictionnary1 = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta");
		final List<String> nameSecondPartDictionnary2 = Arrays.asList("Aldebaran", "Centauri", "Pisces", "Cygnus", "Pegasus", "Dragon", "Andromeda");
		final List<String> sampleTags = Arrays.asList("#mountain", "#crater", "#esa", "#cnsa", "#roscosmos", "#nasa", "#experimental");

		final String exteriorPicturePrefix = "/io/mars/datageneration/files/bases/mars base ";
		final String exteriorPictureSuffix = ".jpg";
		final String interiorPicturePrefix = "/io/mars/datageneration/files/bases/inner base ";
		final String interiorPictureSuffix = ".jpg";

		final FakeBaseListBuilder builder = new FakeBaseListBuilder()
				.withMaxValues(10)
				.withGeosectorIds(basemanagementPAO.selectGeosectorId())
				.withGeoLocations(geoLocations)
				.withNameDictionnaries(nameSecondPartDictionnary2, nameFirstPartDictionnary1)
				.withTagsDictionnary(sampleTags)
				.withPictures(1, exteriorPicturePrefix, exteriorPictureSuffix)
				.withPictures(2, interiorPicturePrefix, interiorPictureSuffix);

		final List<Base> bases = builder.build();
		//base location
		final Base aldebaran = bases.get(0);
		aldebaran.setName("Aldebaran (Paris)");
		aldebaran.setGeoLocation("{ \"lon\": 2.333333 , \"lat\" : 48.866667 } ");
		aldebaran.setCode("Paris-Aldebaran");
		aldebaran.setDescription("The Aldebaran base was the first base of the MMC and managed by Paris."
				+ " The first settlers, led by James T. Kirk, planted the flag in a crater, near a frozen lake.\n"
				+ "The crater is one kilometer deep, which offers a mountain-like landscape.\n"
				+ "The place is rich in minerals, especially in antimony.");
		aldebaran.setTags("#cnes;#crater;#mine");

		final Base centauri = bases.get(1);
		centauri.setName("Centauri (Oslo)");
		centauri.setGeoLocation("{ \"lon\": 10.752245399999993 , \"lat\" : 59.9138688 } ");
		centauri.setCode("Oslo-Centauri");
		centauri.setDescription("The Centauri base is managed by Oslo. "
				+ "The first settlers, led by John König, planted the flag near Utopia Planitia.\n"
				+ "The area is flat and several kilometers long.\n"
				+ "The place is ideal for agriculture, with a soil rich in potassium.");
		centauri.setTags("#esa;#flat;#agriculture");

		final Base pisces = bases.get(2);
		pisces.setName("Pisces (Stockholm)");
		pisces.setGeoLocation("{ \"lon\": 18.06216022  , \"lat\" : 59.3294824 } ");
		pisces.setCode("Stockholm-Pisces");
		pisces.setDescription("The Pisces base is managed by Stockholm. "
				+ "The first settlers, led by Jean-Luc Picard, planted the flag in Olympus Mons.\n"
				+ "The base offers a wonderful landscape around the Olympus Mons which offers a natural watchtower.\n"
				+ "The place is used as a refinery thanks to blast furnaces.");
		pisces.setTags("#esa;#mountain;#refinery");

		final Base cygnus = bases.get(3);
		cygnus.setName("Cygnus (Copenhagen)");
		cygnus.setGeoLocation("{ \"lon\": 12.568337 , \"lat\" : 55.676098 } ");
		cygnus.setCode("Copenhagen-Cygnus");
		cygnus.setDescription("The Cygnus base is managed by Copenhagen. "
				+ "The first settlers, led by Darth Vader, planted the flag in Tharsis.\n"
				+ "The base offers a wonderful maritime landscape and is well-known for its seaside resort and its retirement house\n"
				+ "The place is used as a rest place.");
		cygnus.setTags("#cnes;#seaside;#hollidays");

		final Base dragon = bases.get(4);
		dragon.setName("Dragon (Roma)");
		dragon.setGeoLocation("{ \"lon\": 12.48327333 , \"lat\" : 41.89988 } ");
		dragon.setCode("Roma-Dragon");
		dragon.setDescription("The Dragon base is managed by Roma."
				+ " The first settlers, led by Han Solo, planted the flag in a crater, near a frozen lake.\n"
				+ "The crater is one kilometer deep, which offers a mountain-like landscape.\n"
				+ "The place is rich in minerals, especially in antimony.");
		dragon.setTags("#cnsa;#crater;#mine");

		final Base andromeda = bases.get(5);
		andromeda.setName("Andromeda (London)");
		andromeda.setGeoLocation("{ \"lon\": -0.1255 , \"lat\" : 51.5084 }");
		andromeda.setCode("London-Andromeda");
		andromeda.setDescription("The Andromeda base is managed by London. "
				+ "The first settlers, led by Jim Lovell, planted the flag near Utopia Planitia.\n"
				+ "The area is flat and several kilometers long.\n"
				+ "The place is ideal for agriculture, with a soil rich in potassium.");
		andromeda.setTags("#esa;#powerplant;#energy");

		final Base proxima = bases.get(6);
		proxima.setName("Proxima (Barcelona)");
		proxima.setGeoLocation("{ \"lon\": 2.166117778 , \"lat\" : 41.38961111 } ");
		proxima.setCode("Barcelona-Proxima");
		proxima.setDescription("The Proxima base is managed by Barcelona. "
				+ "The first settlers, led by William Adama, planted the flag in Olympus Mons.\n"
				+ "The base offers a wonderful landscape around the Olympus Mons which offers a natural watchtower.\n"
				+ "The place is used as a refinery thanks to blast furnaces.");
		proxima.setTags("#cnsa;#mountain;#gas");

		final Base cassiopeiae = bases.get(7);
		cassiopeiae.setName("Cassiopeiae (Hamburg)");
		cassiopeiae.setGeoLocation("{ \"lon\": 10.002914 , \"lat\" : 53.561012 } ");
		cassiopeiae.setCode("Hamburg-Cassiopeiae");
		cassiopeiae.setDescription("The Cassiopeiae base is managed by Hamburg. "
				+ "The first settlers, led by HAL, planted the flag in Tharsis.\n"
				+ "The base offers a wonderful maritime landscape and is well-known for its seaside resort and its retirement house\n"
				+ "The place is used as a rest place.");
		cassiopeiae.setTags("#roscomos;#maritime;#hollidays");

		final Base persei = bases.get(8);
		persei.setName("Persei (Moscow)");
		persei.setGeoLocation("{ \"lon\": 37.619183 , \"lat\" : 55.757425}");
		persei.setCode("Moscow-Persei");
		persei.setDescription("The Persei base is managed by Moscow."
				+ " The first settlers, led by Arthur Dallas, planted the flag in a crater, near a frozen lake.\n"
				+ "The crater is one kilometer deep, which offers a mountain-like landscape.\n"
				+ "The place is rich in minerals, especially in antimony.");
		persei.setTags("#roscosmos;#crater;#mine");

		final Base pegasus = bases.get(9);
		pegasus.setName("Pegasus (Munich)");
		pegasus.setGeoLocation("{ \"lon\": 11.576124 , \"lat\" : 48.137154 } ");
		pegasus.setCode("Munich-Pegasus");
		pegasus.setDescription("The Pegasus base is managed by Munich. "
				+ "The first settlers, led by Ellen Louise Ripley, planted the flag near Utopia Planitia.\n"
				+ "The area is flat and several kilometers long.\n"
				+ "The place is ideal for agriculture, with a soil rich in potassium.");
		pegasus.setTags("#nasa;#flat;#farming");

		int baseIdx = 0;
		for (final Base base : bases) {
			baseDAO.create(base);
			baseIdx++;

			//Add picture

			for (final String picturePath : builder.generatePictures(baseIdx)) {
				final VFile pictureFile = fileManager.createFile(
						picturePath.substring(picturePath.lastIndexOf('/') + 1),
						"image/" + picturePath.substring(picturePath.lastIndexOf('.') + 1),
						this.getClass().getResource(picturePath));

				final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(pictureFile));

				final Picture picture = new Picture();
				picture.setBaseId(base.getBaseId());
				picture.setPicturefileId((Long) fileInfo.getURI().getKey());
				pictureDAO.create(picture);
			}
		}
		return bases;

	}

}
