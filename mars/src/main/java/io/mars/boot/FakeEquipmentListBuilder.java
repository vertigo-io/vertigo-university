package io.mars.boot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.dynamo.domain.model.DtList;

public class FakeEquipmentListBuilder {

	private int maxValues;
	private static final int MIN_EQUIPMENT_VALUE = 100;
	private static final int MAX_EQUIPMENT_VALUE = 3000;
	private static final int MIN_RENTING_FEE = 50;
	private static final int MAX_RENTING_FEE = 300;

	private BasemanagementPAO basemanagementPAO;
	private BusinessDAO businessDAO;
	private EquipmentTypeDAO equipmentTypeDAO;

	public FakeEquipmentListBuilder() {
	}

	public FakeEquipmentListBuilder withMaxValues(int maxValues) {
		this.maxValues = maxValues;
		return this;
	}

	public FakeEquipmentListBuilder withSaveCSVPath(String path) {
		// TODO implement saving to csv
		return this;
	}

	public FakeEquipmentListBuilder withEquipmentTypeDAO(EquipmentTypeDAO equipmentTypeDAO) {
		this.equipmentTypeDAO = equipmentTypeDAO;
		return this;
	}

	public FakeEquipmentListBuilder withBusinessDAO(BusinessDAO businessDAO) {
		this.businessDAO = businessDAO;
		return this;
	}

	public FakeEquipmentListBuilder withBasemanagementPAO(BasemanagementPAO basemanagementPAO) {
		this.basemanagementPAO = basemanagementPAO;
		return this;
	}

	public List<Equipment> build() {

		List<Equipment> equipmentList = new ArrayList<Equipment>();
		DtList<EquipmentType> equipmentTypeList = equipmentTypeDAO.selectEquipmentType();
		DtList<Business> businessList = businessDAO.selectBusiness();
		List<Long> baseIdList = basemanagementPAO.selectBaseId();
		List<Long> geosectorIdList = basemanagementPAO.selectGeosectorId();

		for (int currentCounter = 0; currentCounter < maxValues; currentCounter++) {
			EquipmentType currentEquipmentType = equipmentTypeList.get(ThreadLocalRandom.current().nextInt(equipmentTypeList.size()));
			Business currentBusiness = businessList.get(ThreadLocalRandom.current().nextInt(businessList.size()));
			Long currentBaseId = baseIdList.get(ThreadLocalRandom.current().nextInt(baseIdList.size()));
			Long currentGeosectorId = geosectorIdList.get(ThreadLocalRandom.current().nextInt(geosectorIdList.size()));

			Equipment equipment = createEquipment(currentBaseId,
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
		return FakeDataUtils.random.nextInt(101);
	}

	private static String getGeoLocation() {
		return "";
	}

	private static BigDecimal getEquipmentValue() {
		Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_EQUIPMENT_VALUE, MAX_EQUIPMENT_VALUE);
		return BigDecimal.valueOf(randomDouble);
	}

	private static LocalDate getPurchaseDate() {

		LocalDate today = LocalDate.now();
		return today.minus(31 + FakeDataUtils.random.nextInt(3650), ChronoUnit.DAYS);
	}

	private static BigDecimal getRentingFee() {
		Double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_RENTING_FEE, MAX_RENTING_FEE);
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
		Equipment equipment = new Equipment();
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

}
