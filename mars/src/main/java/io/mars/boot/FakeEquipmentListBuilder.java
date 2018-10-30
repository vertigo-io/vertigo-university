package io.mars.boot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.Geosector;
import io.mars.catalog.domain.EquipmentType;

public class FakeEquipmentListBuilder {

	private int maxValues;

	public FakeEquipmentListBuilder() {
	}

	public FakeEquipmentListBuilder withMaxValues(int maxValues) {
		this.maxValues = maxValues;
		return this;
	}

	public FakeEquipmentListBuilder withSaveCSVPath(String path) {
		return this;
	}

	public List<Equipment> build() {

		List<Equipment> equipmentList = new ArrayList<Equipment>();
		List<Long> equipmentTypeIdList = null;
		List<Long> BusinessIdList = null;

		return equipmentList;
	}

	private static Equipment createEquipment(final Base base,
			final Business business,
			final EquipmentType equipmentType,
			final Geosector geoSector,
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
		equipment.base().set(base);
		equipment.business().set(business);
		equipment.equipmentType().set(equipmentType);
		equipment.geosector().set(geoSector);
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
