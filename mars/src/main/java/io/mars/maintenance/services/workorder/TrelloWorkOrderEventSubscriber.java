package io.mars.maintenance.services.workorder;

import java.util.Optional;

import javax.inject.Inject;

import io.mars.maintenance.domain.WorkOrder;
import io.vertigo.adapters.ifttt.IftttAdapter;
import io.vertigo.adapters.ifttt.MakerEvent;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.core.param.ParamManager;

public class TrelloWorkOrderEventSubscriber implements Component {

	private final String iftttApiKey;
	private final String iftttApiUrl;

	@Inject
	public TrelloWorkOrderEventSubscriber(final ParamManager paramManager) {
		iftttApiKey = paramManager.getParam("iftttApiKey").getValue();
		iftttApiUrl = paramManager.getParam("iftttApiUrl").getValue();

	}

	@EventBusSubscribed
	public void onTicketEvent(final WorkOrderEvent workOrderEvent) {
		if (workOrderEvent.getType() == WorkOrderEvent.Type.CREATE) {
			final WorkOrder workOrder = workOrderEvent.getWorkOrder();

			final MakerEvent event = new MakerEvent("work_order_created");

			event.getEventMetadatas().setValue1(workOrder.getCode());
			event.getEventMetadatas().setValue2(workOrder.getName());
			event.getEventMetadatas().setValue3(workOrder.getDescription());

			IftttAdapter.sendMakerEvent(event, iftttApiUrl, iftttApiKey, Optional.empty(), Optional.empty());

		}
	}

}
