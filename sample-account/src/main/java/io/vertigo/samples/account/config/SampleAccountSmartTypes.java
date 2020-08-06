package io.vertigo.samples.account.config;

import io.vertigo.datamodel.impl.smarttype.constraint.ConstraintStringLength;
import io.vertigo.datamodel.smarttype.annotations.Constraint;
import io.vertigo.datamodel.smarttype.annotations.FormatterDefault;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeDefinition;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeProperty;

public enum SampleAccountSmartTypes {

	@SmartTypeDefinition(Long.class)
	@FormatterDefault
	@SmartTypeProperty(property = "storeType", value = "NUMERIC")
	Id,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	@Constraint(clazz = ConstraintStringLength.class, arg = "100", msg = "")
	@SmartTypeProperty(property = "storeType", value = "VARCHAR(100)")
	Code,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	@Constraint(clazz = ConstraintStringLength.class, arg = "100", msg = "")
	@SmartTypeProperty(property = "storeType", value = "VARCHAR(100)")
	Label,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	@Constraint(clazz = ConstraintStringLength.class, arg = "500", msg = "")
	@SmartTypeProperty(property = "storeType", value = "VARCHAR(500)")
	LabelLong,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	@Constraint(clazz = ConstraintStringLength.class, arg = "1000", msg = "")
	@SmartTypeProperty(property = "storeType", value = "VARCHAR(1000)")
	LabelVeryLong,

	@SmartTypeDefinition(Integer.class)
	@FormatterDefault
	@SmartTypeProperty(property = "storeType", value = "NUMERIC")
	Year;

}
