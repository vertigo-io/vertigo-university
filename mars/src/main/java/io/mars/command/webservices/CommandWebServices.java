package io.mars.command.webservices;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.commons.command.CommandDefinition;
import io.vertigo.commons.command.CommandManager;
import io.vertigo.commons.command.CommandParam;
import io.vertigo.commons.command.CommandResponse;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtField;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;
import io.vertigo.util.ClassUtil;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@PathPrefix("/vertigo/commands")
public class CommandWebServices implements WebServices {

	@Inject
	private CommandManager commandManager;
	@Inject
	private StoreManager storeManager;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private CollectionsManager collectionsManager;

	@POST("/_search")
	public List<CommandUi> searchCommands(@InnerBodyParam("prefix") final Optional<String> prefixOpt) {
		return commandManager.searchCommands(prefixOpt.orElse(""))
				.stream()
				.map(CommandWebServices::toCommandUi)
				.collect(Collectors.toList());
	}

	@POST("/_execute")
	public CommandResponse executeCommand(@InnerBodyParam("command") final String command, @InnerBodyParam("params") final String[] params) {
		return commandManager.executeCommand(command, params);
	}

	private static CommandUi toCommandUi(final CommandDefinition commandDefinition) {
		final CommandUi commandUi = new CommandUi();
		commandUi.commandName = commandDefinition.getCommand();
		commandUi.descpription = commandDefinition.getDescription();
		commandUi.commandParams = commandDefinition.getParams();
		return commandUi;
	}

	public static class CommandUi {

		public String commandName;
		public String descpription;
		public List<CommandParam> commandParams;

	}

	@GET("/params/autocomplete")
	public List<Map<String, String>> autocompleteMdList(@QueryParam("terms") final String terms, @QueryParam("entityClass") final String entityClass) {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(ClassUtil.classForName(entityClass, Entity.class));
		final DtListURIForMasterData dtListURIForMasterData = new DtListURIForMasterData(dtDefinition, null);
		Assertion.checkArgument(storeManager.getMasterDataConfig().containsMasterData(dtListURIForMasterData.getDtDefinition()), "Autocomplete can't be use with {0}, it's not a MasterDataList.",
				dtListURIForMasterData.getDtDefinition().getName());

		//-----
		final DtField labelDtField = dtDefinition.getDisplayField().get();

		final Collection<DtField> searchedFields = Collections.singletonList(labelDtField);
		final DtList<Entity> results;
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) { //Open a transaction because all fields are indexed. If there is a MDL it was load too.
			final DtList dtList = storeManager.getDataStore().findAll(dtListURIForMasterData);
			final UnaryOperator<DtList<DtObject>> fullTextFilter = collectionsManager.createIndexDtListFunctionBuilder()
					.filter(terms != null ? terms : "", 20, searchedFields)
					.build();
			results = fullTextFilter.apply(dtList);
		}

		return results.stream()
				.map(element -> {
					final Map<String, String> asMap = new HashMap<>();
					asMap.put("urn", element.getUID().urn());
					asMap.put("label", String.valueOf(labelDtField.getDataAccessor().getValue(element)));
					return asMap;
				}).collect(Collectors.toList());
	}

}
