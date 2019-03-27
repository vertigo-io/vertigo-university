package io.mars.catalog.jobs.supplier;

import javax.inject.Inject;

import io.mars.catalog.services.supplier.SupplierServices;
import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

public class CleanAllSuppliers extends AbstractActivityEngine {

	@Inject
	private SupplierServices supplierServices;

	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		supplierServices.cleanAll();
		workspace.setSuccess();
		return workspace;
	}

}
