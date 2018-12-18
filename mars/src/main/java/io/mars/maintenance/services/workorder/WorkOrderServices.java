package io.mars.maintenance.services.workorder;

import javax.inject.Inject;

import io.mars.domain.DtDefinitions.WorkOrderFields;
import io.mars.maintenance.dao.WorkOrderDAO;
import io.mars.maintenance.domain.WorkOrder;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.lang.Assertion;

@Transactional
public class WorkOrderServices implements Component {

	@Inject
	private WorkOrderDAO workOrderDAO;

	public WorkOrder getWorkOrderFromId(final Long workOrderId) {
		return workOrderDAO.get(workOrderId);
	}

	public void save(final WorkOrder workOrder) {
		workOrderDAO.save(workOrder);
	}

	public DtList<WorkOrder> getWorkOrders(final DtListState dtListState) {
		return workOrderDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}

	public DtList<WorkOrder> getWorkOrdersByTicketId(final Long ticketId) {
		Assertion.checkNotNull(ticketId);
		//---
		return workOrderDAO.findAll(
				Criterions.isEqualTo(WorkOrderFields.TICKET_ID, ticketId), Integer.MAX_VALUE);
	}

	public DtList<WorkOrder> getLastWorkOrders() {
		return workOrderDAO.getLastWorkOrders();
	}

}
