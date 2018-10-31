package io.mars.datageneration;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.mars.maintenance.dao.TicketDAO;
import io.mars.maintenance.dao.WorkOrderDAO;
import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.core.component.Component;

public class DataGenerator implements Component {

	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Inject
	private TicketDAO ticketDAO;

	@Inject
	private WorkOrderDAO workOrderDAO;

	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)
	public void generateTicketsAndWorkOrders() {
		//LOGGER.info("####### je suis passé dans le démon\n");
	}
}
