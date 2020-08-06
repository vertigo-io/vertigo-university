package io.vertigo.samples.quarto.config;

import io.vertigo.datamodel.smarttype.annotations.FormatterDefault;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeDefinition;

public enum SampleQuartoSmartTypes {

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	Text,

}
