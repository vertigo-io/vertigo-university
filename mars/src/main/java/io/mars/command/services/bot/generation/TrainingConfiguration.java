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
		public String type;
		public String dtDefinition;
		public String dtField;
		public Integer limit;
		public List<String> values;
	}
}
