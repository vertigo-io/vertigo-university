package io.mars.basemanagement.datageneration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseTypeEnum;
import io.mars.datageneration.DataGenerator;
import io.mars.datageneration.FakeDataUtil;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

@SuppressWarnings("rawtypes")
public final class FakeBaseListBuilder implements Builder {

	private List<String> myNameFirstPartDictionnary;
	private List<String> myNameSecondPartDictionnary;
	private List<String> myTagsDictionnary;
	private List<Long> myGeosectorIds;

	private int myMaxValues = 0;

	private static final int MIN_ASSETS_VALUE = 10000;
	private static final int MAX_ASSETS_VALUE = 100000;

	private static final int MIN_RENTING_FEE = 1000;
	private static final int MAX_RENTING_FEE = 4000;
	private static final int MIN_TAG_NUMBER = 1;
	private static final int MAX_TAG_NUMBER = 3;

	public FakeBaseListBuilder withNameDictionnaries(final List<String> nameFirstPartDictionnary, final List<String> nameSecondPartDictionnary) {
		myNameFirstPartDictionnary = nameFirstPartDictionnary;
		myNameSecondPartDictionnary = nameSecondPartDictionnary;
		return this;
	}

	public FakeBaseListBuilder withSaveCSVPath(final String path) {
		return this;
	}

	public FakeBaseListBuilder withMaxValues(final int maxValues) {
		myMaxValues = maxValues;
		return this;
	}

	public FakeBaseListBuilder withGeosectorIds(final List<Long> geosectorIds) {
		Assertion.checkNotNull(geosectorIds);
		//---
		myGeosectorIds = geosectorIds;
		return this;
	}

	public FakeBaseListBuilder withTagsDictionnary(final List<String> sampleTags) {
		myTagsDictionnary = sampleTags;
		return this;
	}

	private static String getTagsFromDictionnary(final List<String> tagDictionnary) {
		String tagString = "";
		final int tagNumber = ThreadLocalRandom.current().nextInt(MIN_TAG_NUMBER, MAX_TAG_NUMBER);
		for (int i = 0; i < tagNumber; i++) {
			tagString += tagDictionnary.get(ThreadLocalRandom.current().nextInt(tagDictionnary.size() - 1)) + " ";
		}
		return tagString.substring(0, tagString.length() - 1);

	}

	private static String getCodeFromBaseName(final String firstPart, final String secondPart, final int baseIndex) {
		final String code = firstPart + "-" + baseIndex;
		return code;
	}

	private static LocalDate getCreationDate() {

		final LocalDate today = LocalDate.now();
		return today.minus(31 + DataGenerator.RND.nextInt(3650), ChronoUnit.DAYS);
	}

	private static int getHealthLevel() {
		return DataGenerator.RND.nextInt(101);
	}

	private static String getDescription(final String firstPart, final String secondPart, final int healthLevel, final LocalDate creationDate) {
		return "The " + firstPart + " " + secondPart + " base was created on " + creationDate + ". Its current HealthLevel is :" + healthLevel + ".";
	}

	private static String getGeoLocation() {
		return "";
	}

	private static BigDecimal getAssetsValue() {
		final Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_ASSETS_VALUE, MAX_ASSETS_VALUE);
		return BigDecimal.valueOf(randomDouble);
	}

	private static BigDecimal getRentingFee() {
		final Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_RENTING_FEE, MAX_RENTING_FEE);
		return BigDecimal.valueOf(randomDouble);
	}

	private static Base createBase(final BaseTypeEnum baseTypeEnumValue,
			final String baseName,
			final String baseCode,
			final int healthLevel,
			final LocalDate creationDate,
			final String description,
			final String geoLocation,
			final BigDecimal assetsValue,
			final BigDecimal rentingFee,
			final Long geosectorId,
			final String tagString) {
		final Base base = new Base();
		base.setCode(baseCode);
		base.setName(baseName);
		base.baseType().setEnumValue(baseTypeEnumValue);
		base.setHealthLevel(healthLevel);
		base.setCreationDate(creationDate);
		base.setDescription(description);
		base.setGeoLocation(geoLocation);
		base.setAssetsValue(assetsValue);
		base.setRentingFee(rentingFee);
		base.geosector().setId(geosectorId);
		base.setTags(tagString);
		return base;
	}

	@Override
	public List<Base> build() {
		Assertion.checkNotNull(myGeosectorIds);
		//---
		final List<Base> bases = new ArrayList<>();

		int currentCounter = 0;
		for (final String firstPart : myNameFirstPartDictionnary) {
			for (final String secondPart : myNameSecondPartDictionnary) {

				final Long currentGeosectorId = myGeosectorIds.get(ThreadLocalRandom.current().nextInt(myGeosectorIds.size()));
				final LocalDate creationDate = getCreationDate();
				final int healthLevel = getHealthLevel();
				final String description = getDescription(firstPart, secondPart, healthLevel, creationDate);

				bases.add(createBase(FakeDataUtil.randomEnum(BaseTypeEnum.class),
						firstPart + " " + secondPart,
						getCodeFromBaseName(firstPart, secondPart, currentCounter),
						healthLevel,
						creationDate,
						description,
						getGeoLocation(),
						getAssetsValue(),
						getRentingFee(),
						currentGeosectorId,
						getTagsFromDictionnary(myTagsDictionnary)));
				currentCounter++;

				if (currentCounter >= myMaxValues) {
					break;
				}
			}
			if (currentCounter >= myMaxValues) {
				break;
			}
		}
		return bases;
	}

}
