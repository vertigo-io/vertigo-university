package io.mars.boot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseTypeEnum;
import io.vertigo.lang.Assertion;

public class FakeBaseListBuilder {

	private List<String> myNameFirstPartDictionnary;
	private List<String> myNameSecondPartDictionnary;
	private List<Long> myGeosectorIdList;
	private int myMaxValues = 0;

	private static final int MIN_ASSETS_VALUE = 10000;
	private static final int MAX_ASSETS_VALUE = 100000;

	private static final int MIN_RENTING_FEE = 1000;
	private static final int MAX_RENTING_FEE = 4000;

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

	public FakeBaseListBuilder withGeosectorIdList(final List<Long> geosectorIdList) {
		Assertion.checkNotNull(geosectorIdList);
		//---
		myGeosectorIdList = geosectorIdList;
		return this;
	}

	public List<Base> build() {
		Assertion.checkNotNull(myGeosectorIdList);
		//---
		final List<Base> baseList = new ArrayList<>();

		int currentCounter = 0;
		for (final String firstPart : myNameFirstPartDictionnary) {
			for (final String secondPart : myNameSecondPartDictionnary) {

				final Long currentGeosectorId = myGeosectorIdList.get(ThreadLocalRandom.current().nextInt(myGeosectorIdList.size()));
				final LocalDate creationDate = getCreationDate();
				final int healthLevel = getHealthLevel();
				final String description = getDescription(firstPart, secondPart, healthLevel, creationDate);
				
				baseList.add(createBase(FakeDataUtils.randomEnum(BaseTypeEnum.class),
						firstPart + " " + secondPart,
						getCodeFromBaseName(firstPart, secondPart, currentCounter),
						healthLevel,
						creationDate,
						description,
						getGeoLocation(),
						getAssetsValue(),
						getRentingFee(),
						currentGeosectorId));
				currentCounter++;

				if (currentCounter >= myMaxValues) {
					break;
				}
			}
			if (currentCounter >= myMaxValues) {
				break;
			}
		}
		return baseList;
	}

	private static String getCodeFromBaseName(final String firstPart, final String secondPart, final int baseIndex) {
		final String code = firstPart + "-" + baseIndex;
		return code;
	}

	private static LocalDate getCreationDate() {

		final LocalDate today = LocalDate.now();
		return today.minus(31 + FakeDataUtils.random.nextInt(3650), ChronoUnit.DAYS);
	}

	private static int getHealthLevel() {
		return FakeDataUtils.random.nextInt(101);
	}

	private static String getDescription(String firstPart, String secondPart, int healthLevel, LocalDate creationDate) {
		return "The " + firstPart + " " + secondPart + " base was created on " + creationDate +". Its current HealthLevel is :" + healthLevel +".";
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
			final Long geosectorId) {
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
		return base;
	}
}
