package io.mars.support.boot;

import javax.inject.Inject;

import io.mars.basemanagement.jobs.DecommissionEquipmentActivityEngine;
import io.mars.basemanagement.jobs.DecommissioningReportActivityEngine;
import io.mars.catalog.jobs.supplier.CleanAllSuppliers;
import io.mars.catalog.jobs.supplier.DownloadAndUnizpSuppliersActivityEngine;
import io.mars.catalog.jobs.supplier.ParseCSVSuppliersActivityEngine;
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
				.withCronExpression("0 0/1 * ? * * *")
				.addActivity("DECOMMISSION", "Decommission equipmenents", DecommissionEquipmentActivityEngine.class)
				.addActivity("REPORT", "Produce a file report of the decommisioned equipments", DecommissioningReportActivityEngine.class)
				.build();
		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final ProcessDefinition importSuppliers = ProcessDefinition.builder("IMPORT_SUPPLIERS", "Import Suppliers")
				.addActivity("CLEAN", "Clean", CleanAllSuppliers.class)
				.addActivity("INDEX", "Index", ParseCSVSuppliersActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(importSuppliers);

		final ProcessDefinition downloadSuppliers = ProcessDefinition.builder("DOWNLOAD_SUPPLIERS", "Download Suppliers")
				.addActivity("DOWNLOAD", "Download", DownloadAndUnizpSuppliersActivityEngine.class)
				.addInitialParam("stockSireneUniteLegaleUrl", "http://files.data.gouv.fr/insee-sirene/StockUniteLegale_utf8.zip")
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(downloadSuppliers);

	}

}
