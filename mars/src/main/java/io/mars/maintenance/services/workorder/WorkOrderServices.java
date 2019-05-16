package io.mars.maintenance.services.workorder;

import java.time.LocalDate;

import javax.inject.Inject;

import io.mars.domain.DtDefinitions.WorkOrderFields;
import io.mars.maintenance.dao.WorkOrderDAO;
import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.domain.WorkOrderStatusEnum;
import io.vertigo.commons.eventbus.EventBusManager;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.lang.Assertion;

@Transactional
public class WorkOrderServices implements Component {

	@Inject
	private WorkOrderDAO workOrderDAO;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private EventBusManager eventBusManager;

	public WorkOrder getWorkOrderFromId(final Long workOrderId) {
		return workOrderDAO.get(workOrderId);
	}

	public void save(final WorkOrder workOrder) {
		workOrderDAO.save(workOrder);
	}

	public DtList<WorkOrder> getWorkOrdersByTicketId(final Long ticketId) {
		Assertion.checkNotNull(ticketId);
		//---
		return workOrderDAO.findAll(
				Criterions.isEqualTo(WorkOrderFields.ticketId, ticketId), DtListState.defaultOf(WorkOrder.class));
	}

	public DtList<WorkOrder> getLastWorkOrders() {
		return workOrderDAO.getLastWorkOrders();
	}

	public void createWorkOrder(final WorkOrder workOrder) {
		Assertion.checkArgument(workOrder.getWoId() == null, "No id should be provided for a new ticket");
		//---
		workOrder.setDateCreated(LocalDate.now());
		workOrder.workOrderStatus().setEnumValue(WorkOrderStatusEnum.pending);
		workOrderDAO.save(workOrder);
		transactionManager.getCurrentTransaction()
				.addAfterCompletion(txCommited -> {
					if (txCommited) {
						eventBusManager.post(new WorkOrderEvent(WorkOrderEvent.Type.CREATE, workOrder));
					}
				});
	}

}
