package io.mars.datageneration;

import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.core.component.Component;

public class DataGenerator implements Component {

	//private final Logger logger = LogManager.getLogger(this.getClass());

	//@Inject
	//private TicketDAO ticketDAO;

	//@Inject
	//private WorkOrderDAO workOrderDAO;

	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)
	public void generateTicketsAndWorkOrders() {
		//logger.info("####### je suis passé dans le démon\n");
	}
}
