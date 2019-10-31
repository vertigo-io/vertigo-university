package io.mars.command.services.bot.generation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.command.services.bot.generation.TrainingConfiguration.CommandParamTrainingConfiguration;
import io.vertigo.app.Home;
import io.vertigo.commons.command.CommandDefinition;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.metamodel.DataAccessor;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.VSystemException;

public class TrainSetProvider implements Component {

	@Inject
	private StoreManager storeManager;
	@Inject
	private VTransactionManager transactionManager;

	public List<String[]> getTrainSet(final CommandDefinition commandDefinition, final TrainingConfiguration trainingConfiguration) {
		if (trainingConfiguration.containsKey(commandDefinition.getCommand())) {
			if (commandDefinition.getParams().isEmpty()) {
				return Collections.singletonList(new String[0]);
			}

			return ((List<List<String>>) product(trainingConfiguration.get(commandDefinition.getCommand()).entrySet()
					.stream()
					.map(entry -> retrievePossibleValues(entry.getValue()))
					.toArray(List[]::new)))
							.stream()
							.map(listOfValues -> listOfValues.toArray(new String[listOfValues.size()]))
							.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private static List<?> product(final List<?>... a) {
		if (a.length >= 2) {
			List<?> product = a[0];
			for (int i = 1; i < a.length; i++) {
				product = binaryProduct(product, a[i]);
			}
			return product;
		} else if (a.length == 1) {
			return a[0].stream()
					.map(Collections::singletonList)
					.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	private static List<?> binaryProduct(final List<?> a, final List<?> b) {
		return a.stream()
				.map(e1 -> b.stream().map(e2 -> Arrays.asList(e1, e2)).collect(Collectors.toList()))
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	private final List<String> retrievePossibleValues(final CommandParamTrainingConfiguration commandParamTrainingConfiguration) {
		Assertion.checkArgNotEmpty(commandParamTrainingConfiguration.getType());

		switch (commandParamTrainingConfiguration.getType()) {
			case "fromDb":
				try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
					final DtDefinition dtDefinition = Home.getApp().getDefinitionSpace().resolve(commandParamTrainingConfiguration.getDtDefinition(), DtDefinition.class);
					final DataAccessor dtFieldDataAccessor = dtDefinition.getField(commandParamTrainingConfiguration.getDtField()).getDataAccessor();
					return storeManager.getDataStore().find(dtDefinition, Criterions.alwaysTrue(), DtListState.of(commandParamTrainingConfiguration.getLimit()))
							.stream()
							.map(entity -> String.valueOf(dtFieldDataAccessor.getValue(entity)))
							.collect(Collectors.toList());
				}
			case "static":
				Assertion.checkNotNull(commandParamTrainingConfiguration.getValues());
				return commandParamTrainingConfiguration.getValues();
			default:
				throw new VSystemException("Unsupported training type : ", commandParamTrainingConfiguration.getType());
		}
	}
}
