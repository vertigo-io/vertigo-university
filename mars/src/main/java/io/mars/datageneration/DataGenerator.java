package io.mars.datageneration;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.core.component.Component;

public class DataGenerator implements Component {

	private static final Long RANDOM_SEED = 1337L;
	private static final Random rnd = new Random(RANDOM_SEED);
	private final Logger logger = LogManager.getLogger(this.getClass());

	//@Inject
	//private TicketDAO ticketDAO;

	//@Inject
	//private WorkOrderDAO workOrderDAO;

	public void generatePastData() {
		// appel aux autres fonctions

	}

	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)
	public void generateTicketsAndWorkOrders() {
		logger.info(rnd.nextDouble());
	}

}
