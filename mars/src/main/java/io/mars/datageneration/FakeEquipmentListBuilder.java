package io.mars.datageneration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;

public class FakeEquipmentListBuilder {

	private int myMaxValues;
	private static final int MIN_EQUIPMENT_VALUE = 100;
	private static final int MAX_EQUIPMENT_VALUE = 3000;
	private static final int MIN_RENTING_FEE = 50;
	private static final int MAX_RENTING_FEE = 300;
	private static Random rnd = null;

	private DtList<Business> myBusinessList;
	private DtList<EquipmentType> myEquipmentTypeList;
	private List<Long> myBaseIdList;
	private List<Long> myGeosectorIdList;

	public FakeEquipmentListBuilder() {
	}

	public FakeEquipmentListBuilder withRandomSeed(final Long randomSeed) {
		rnd = new Random(randomSeed);
		return this;
	}

	public FakeEquipmentListBuilder withMaxValues(final int maxValues) {
		myMaxValues = maxValues;
		return this;
	}

	public FakeEquipmentListBuilder withSaveCSVPath(final String path) {
		// TODO implement saving to csv
		return this;
	}

	public FakeEquipmentListBuilder withEquipmentTypeList(final DtList<EquipmentType> equipmentTypeList) {
		Assertion.checkNotNull(equipmentTypeList);
		//---
		myEquipmentTypeList = equipmentTypeList;
		return this;
	}

	public FakeEquipmentListBuilder withBusinessList(final DtList<Business> businessList) {
		Assertion.checkNotNull(businessList);
		//---
		myBusinessList = businessList;
		return this;
	}

	public FakeEquipmentListBuilder withBaseIdList(final List<Long> baseIdList) {
		Assertion.checkNotNull(baseIdList);
		//---
		myBaseIdList = baseIdList;
		return this;
	}

	public FakeEquipmentListBuilder withGeosectorIdList(final List<Long> geosectorIdList) {
		Assertion.checkNotNull(geosectorIdList);
		//---
		myGeosectorIdList = geosectorIdList;
		return this;
	}

	public List<Equipment> build() {
		Assertion.checkNotNull(myEquipmentTypeList);
		Assertion.checkNotNull(myBusinessList);
		Assertion.checkNotNull(myBaseIdList);
		Assertion.checkNotNull(myGeosectorIdList);
		Assertion.checkNotNull(rnd);
		final List<Equipment> equipmentList = new ArrayList<>();

		for (int currentCounter = 0; currentCounter < myMaxValues; currentCounter++) {

			final EquipmentType currentEquipmentType = myEquipmentTypeList.get(rnd.nextInt(myEquipmentTypeList.size()));
			final Business currentBusiness = myBusinessList.get(rnd.nextInt(myBusinessList.size()));
			final Long currentBaseId = myBaseIdList.get(rnd.nextInt(myBaseIdList.size()));
			final Long currentGeosectorId = myGeosectorIdList.get(rnd.nextInt(myGeosectorIdList.size()));

			final Equipment equipment = createEquipment(currentBaseId,
					currentBusiness,
					currentEquipmentType,
					currentGeosectorId,
					getCode(currentEquipmentType.getLabel(), currentBusiness.getName(), currentCounter),
					getEquipmentValue(),
					getGeoLocation(),
					getHealthLevel(),
					getEquipmentName(currentEquipmentType.getLabel(), currentBusiness.getName(), currentCounter),
					getDescription(),
					getPurchaseDate(),
					getRentingFee(),
					getTags());
			equipmentList.add(equipment);
		}
		return equipmentList;
	}

	private static String getTags() {
		return "";
	}

	private static String getCode(final String equipmentTypeName, final String businessName, final int equipmentIndex) {
		return equipmentTypeName.substring(0, 1) + "-" + businessName.substring(0, 1) + "-" + equipmentIndex;
	}

	private static String getDescription() {
		return "";
	}

	private static String getEquipmentName(final String equipmentTypeName, final String businessName, final int equipmentIndex) {
		return equipmentTypeName + " " + equipmentTypeName.substring(0, 1) + "-" + businessName.substring(0, 1) + "-" + equipmentIndex;
	}

	private static int getHealthLevel() {
		return rnd.nextInt(101);
	}

	private static String getGeoLocation() {
		return "";
	}

	private static BigDecimal getEquipmentValue() {
		final Double randomDouble = myNextDouble(MIN_EQUIPMENT_VALUE, MAX_EQUIPMENT_VALUE, rnd);
		return BigDecimal.valueOf(randomDouble);
	}

	private static LocalDate getPurchaseDate() {

		final LocalDate today = LocalDate.now();
		return today.minus(31 + rnd.nextInt(3650), ChronoUnit.DAYS);
	}

	private static BigDecimal getRentingFee() {
		final Double randomDouble = myNextDouble(MIN_RENTING_FEE, MAX_RENTING_FEE, rnd);
		return BigDecimal.valueOf(randomDouble);
	}

	private static Equipment createEquipment(final Long baseId,
			final Business business,
			final EquipmentType equipmentType,
			final Long geoSectorId,
			final String code,
			final BigDecimal equipmentValue,
			final String geoLocation,
			final Integer healthLevel,
			final String name,
			final String description,
			final LocalDate purchaseDate,
			final BigDecimal rentingFee,
			final String tags) {
		final Equipment equipment = new Equipment();
		equipment.base().setId(baseId);
		equipment.business().set(business);
		equipment.equipmentType().set(equipmentType);
		equipment.geosector().setId(geoSectorId);
		equipment.setCode(code);
		equipment.setEquipmentValue(equipmentValue);
		equipment.setGeoLocation(geoLocation);
		equipment.setHealthLevel(healthLevel);
		equipment.setName(name);
		equipment.setDescription(description);
		equipment.setPurchaseDate(purchaseDate);
		equipment.setRentingFee(rentingFee);
		equipment.setTags(tags);
		return equipment;
	}

	private static Double myNextDouble(final int min, final int max, final Random random) {
		Assertion.checkNotNull(random, "Random Generator must be initialised before use");
		//
		return min + random.nextDouble() * (max - min);
	}

}
