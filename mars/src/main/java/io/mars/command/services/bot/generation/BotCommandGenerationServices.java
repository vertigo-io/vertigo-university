package io.mars.command.services.bot.generation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import com.google.gson.Gson;

import freemarker.cache.ClassTemplateLoader;
import freemarker.core.Configurable;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.mars.command.services.bot.generation.model.CommandModel;
import io.mars.command.services.bot.generation.model.CommandTrainingModel;
import io.vertigo.app.Home;
import io.vertigo.commons.command.CommandDefinition;
import io.vertigo.core.component.Component;
import io.vertigo.core.locale.MessageText;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dashboard.ui.DashboardRouter;
import io.vertigo.lang.WrappedException;

public class BotCommandGenerationServices implements Component {

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private TrainSetProvider trainSetProvider;

	public String generateRasaDomain() {
		final List<CommandModel> commandModels = Home.getApp().getDefinitionSpace().getAll(CommandDefinition.class)
				.stream()
				.map(CommandModel::new)
				.collect(Collectors.toList());

		final Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("commands", commandModels);
		return generateFile(dataModel, "template/domain.ftl");
	}

	public String generateRasaStories() {
		final List<CommandModel> commandModels = Home.getApp().getDefinitionSpace().getAll(CommandDefinition.class)
				.stream()
				.map(CommandModel::new)
				.collect(Collectors.toList());

		final Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("commands", commandModels);
		return generateFile(dataModel, "template/stories.ftl");
	}

	public String generateNlu() {
		final TrainingConfiguration trainingConfiguration = new Gson().fromJson(parseFile(resourceManager.resolve("/io/mars/command/services/bot/generation/trainingConfiguration.json")), TrainingConfiguration.class);

		final List<CommandTrainingModel> commandTrainingModels = Home.getApp().getDefinitionSpace().getAll(CommandDefinition.class)
				.stream()
				.map(commandDefinition -> new CommandTrainingModel(
						commandDefinition,
						generateExamples(commandDefinition, trainSetProvider.getTrainSet(commandDefinition, trainingConfiguration))))
				.collect(Collectors.toList());

		final Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("commands", commandTrainingModels);
		return generateFile(dataModel, "template/nlu.ftl");
	}

	private static String parseFile(final URL url) {
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			final StringBuilder buff = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				buff.append(line);
				line = reader.readLine();
				buff.append("\r\n");
			}
			return buff.toString();
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Error reading json file : '{0}'", url);
		}
	}

	private static final List<String> generateExamples(final CommandDefinition commandDefinition, final List<String[]> trainSet) {
		return commandDefinition.getQuestions()
				.stream()
				.flatMap(question -> {
					if (trainSet.isEmpty() && commandDefinition.getParams().isEmpty()) {
						return Collections.singletonList(question).stream();
					} else {
						return trainSet.stream().map(args -> MessageText.of(question, (Serializable[]) transformParamsForNlu(commandDefinition, args)).getDisplay());
					}
				})
				.collect(Collectors.toList());
	}

	private static final String[] transformParamsForNlu(final CommandDefinition commandDefinition, final String[] trainingValues) {
		final String commandNameSnakeCase = commandDefinition.getCommand().substring(1).replace('/', '_');
		return IntStream
				.range(0, trainingValues.length)
				.mapToObj(index -> "[" + trainingValues[index] + "](" + commandNameSnakeCase + "_param_" + index + ")")
				.toArray(String[]::new);
	}

	private static final String generateFile(final Map<String, Object> model, final String templateName) {
		try {
			final Configuration configuration = createFreemarkerConfiguration();
			final Template template = configuration.getTemplate(templateName);
			final StringWriter stringWriter = new StringWriter();
			template.process(model, stringWriter);
			return stringWriter.toString();
		} catch (TemplateException | IOException e) {
			throw WrappedException.wrap(e);
		}
	}

	private static final Configuration createFreemarkerConfiguration() {
		final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setTemplateLoader(new ClassTemplateLoader(DashboardRouter.class, "/"));
		configuration.setClassForTemplateLoading(BotCommandGenerationServices.class, "");
		final BeansWrapperBuilder beansWrapperBuilder = new BeansWrapperBuilder(Configuration.VERSION_2_3_23);
		beansWrapperBuilder.setSimpleMapWrapper(true);
		configuration.setObjectWrapper(beansWrapperBuilder.build());
		try {
			configuration.setSetting(Configurable.NUMBER_FORMAT_KEY, "computer");
		} catch (final TemplateException e) {
			throw WrappedException.wrap(e);
		}
		return configuration;
	}

}
