package io.mars.boot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseTypeEnum;

public class FakeBaseListBuilder {

	private List<String> nameFirstPartdictionnary;
	private List<String> nameSecondPartdictionnary;
	private int maxValues;

	private static final int MIN_ASSETS_VALUE = 10000;
	private static final int MAX_ASSETS_VALUE = 100000;

	private static final int MIN_RENTING_FEE = 1000;
	private static final int MAX_RENTING_FEE = 4000;

	public FakeBaseListBuilder() {
	}

	public FakeBaseListBuilder withNameDictionnaries(List<String> nameFirstPartDictionnary, List<String> nameSecondPartDictionnary) {
		this.nameFirstPartdictionnary = nameFirstPartDictionnary;
		this.nameSecondPartdictionnary = nameSecondPartDictionnary;
		return this;
	}

	public FakeBaseListBuilder withSaveCSVPath(String path) {
		return this;
	}

	public FakeBaseListBuilder withMaxValues(int maxValues) {
		this.maxValues = maxValues;
		return this;
	}

	public List<Base> build() {
		List<Base> baseList = new ArrayList<Base>();

		int currentCounter = 0;
		for (String firstPart : nameFirstPartdictionnary) {
			for (String secondPart : nameSecondPartdictionnary) {

				baseList.add(createBase(FakeDataUtils.randomEnum(BaseTypeEnum.class),
						firstPart + " " + secondPart,
						getCodeFromBaseName(firstPart, secondPart, currentCounter),
						getHealthLevel(),
						getCreationDate(),
						getDescription(),
						getGeoLocation(),
						getAssetsValue(),
						getRentingFee()));
				currentCounter++;

				if (currentCounter >= maxValues) {
					break;
				}
			}
			if (currentCounter >= maxValues) {
				break;
			}
		}
		return baseList;
	}

	private static String getCodeFromBaseName(String firstPart, String secondPart, int baseIndex) {
		String code = firstPart + "-" + baseIndex;
		return code;
	}

	private static LocalDate getCreationDate() {

		LocalDate today = LocalDate.now();
		return today.minus(31 + FakeDataUtils.random.nextInt(3650), ChronoUnit.DAYS);
	}

	private static int getHealthLevel() {
		return FakeDataUtils.random.nextInt(101);
	}

	private static String getDescription() {
		return "";
	}

	private static String getGeoLocation() {
		return "";
	}

	private static BigDecimal getAssetsValue() {
		Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_ASSETS_VALUE, MAX_ASSETS_VALUE);
		return BigDecimal.valueOf(randomDouble);
	}

	private static BigDecimal getRentingFee() {
		Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_RENTING_FEE, MAX_RENTING_FEE);
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
			final BigDecimal rentingFee) {
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
		return base;
	}
}
