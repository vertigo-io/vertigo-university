package io.mars.command.services.bot.generation;

import java.util.HashMap;
import java.util.List;

import io.mars.command.services.bot.generation.TrainingConfiguration.CommandTrainingConfiguration;

public class TrainingConfiguration extends HashMap<String, CommandTrainingConfiguration> {

	private static final long serialVersionUID = 4083058124283507731L;

	public static class CommandTrainingConfiguration extends HashMap<Integer, CommandParamTrainingConfiguration> {

		private static final long serialVersionUID = -7376891025825249287L;

	}

	public static class CommandParamTrainingConfiguration {

		private String type;
		private String dtDefinition;
		private String dtField;
		private Integer limit;
		private List<String> values;

		public String getType() {
			return type;
		}

		public void setType(final String type) {
			this.type = type;
		}

		public String getDtDefinition() {
			return dtDefinition;
		}

		public void setDtDefinition(final String dtDefinition) {
			this.dtDefinition = dtDefinition;
		}

		public String getDtField() {
			return dtField;
		}

		public void setDtField(final String dtField) {
			this.dtField = dtField;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(final Integer limit) {
			this.limit = limit;
		}

		public List<String> getValues() {
			return values;
		}

		public void setValues(final List<String> values) {
			this.values = values;
		}

	}
}
