package io.mars.boot;

import javax.inject.Inject;

import io.mars.basemanagement.jobs.DecommissionEquipmentActivityEngine;
import io.mars.basemanagement.jobs.DecommissioningReportActivityEngine;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessDefinition;

/**
 * Initialisation des processus gérés par Orchestra
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OrchestraInitializer implements ComponentInitializer {

	@Inject
	private OrchestraDefinitionManager orchestraDefinitionManager;

	/** {@inheritDoc} */
	@Override
	public void init() {

		final ProcessDefinition processDefinition = ProcessDefinition.builder("EQUIPMENT_DECOMMISSIONING", "Equipment decommissioning")
				.withCronExpression("0 0 2 ? * * *")
				.addActivity("DECOMMISSION", "Decommission equipmenents", DecommissionEquipmentActivityEngine.class)
				.addActivity("REPORT", "Produce a file report of the decommisioned equipments", DecommissioningReportActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

	}

}
