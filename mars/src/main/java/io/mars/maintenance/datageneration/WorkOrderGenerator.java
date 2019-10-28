package io.mars.maintenance.datageneration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import io.mars.datageneration.DataGenerator;
import io.mars.maintenance.dao.WorkOrderDAO;
import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.domain.WorkOrderStatusEnum;
import io.vertigo.core.component.Component;

public class WorkOrderGenerator implements Component {

	// a placer sur la bonne méthode
	//	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)

	//private static final Instant GenesisDate = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
	//private static final int CHUNK_SIZE = 1000;
	private static final int MAX_WORKORDERS_PER_TICKET = 3;
	private static final int MAX_WORKORDER_DATE_OFFSET = 5;
	private static final int MAX_DUE_DATE_DELAY = 4;
	/*
		@Inject
		private VTransactionManager transactionManager;
	
		@Inject
		private EquipmentDAO equipmentDAO;
	
		@Inject
		private TicketDAO ticketDAO;
	*/
	@Inject
	private WorkOrderDAO workOrderDAO;

	/*
	public void generatePastWorkOrders(final Instant instant, final ChronoUnit chronoUnit, final int step) {
	
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
	
			int nbRecordsFound = CHUNK_SIZE;
			Long previousLastId = 0L;
	
			while (nbRecordsFound == CHUNK_SIZE) {
				final DtList<Ticket> tickets = ticketDAO.loadTicketssByChunk(new Long(CHUNK_SIZE), previousLastId, instant);
				nbRecordsFound = tickets.size();
				if (nbRecordsFound > 0) {
					previousLastId = tickets.get(nbRecordsFound - 1).getEquipmentId();
	
					for (final Ticket ticket : tickets) {
						createWorkOrdersForTicket(ticket, instant, chronoUnit, step);
					}
				}
			}
	
			tx.commit();
		}
	}
	
	*/

	public void createWorkOrdersForTicket(final Ticket ticket, final LocalDate nowLocalDate) {

		final LocalDate ticketDateCreated = ticket.getDateCreated();
		final int numberOfWorkOrdersForTicket = DataGenerator.RND.nextInt(MAX_WORKORDERS_PER_TICKET);

		for (int i = 0; i < numberOfWorkOrdersForTicket; i++) {
			final int dateOffset = DataGenerator.RND.nextInt(MAX_WORKORDER_DATE_OFFSET);
			final int closeDateOffset = DataGenerator.RND.nextInt(MAX_WORKORDER_DATE_OFFSET + 5);

			final WorkOrder workOrder = new WorkOrder();
			final LocalDate dateCreated = ticketDateCreated.plus(dateOffset, ChronoUnit.DAYS);
			workOrder.setDateCreated(dateCreated);

			workOrder.setDateClosed(dateCreated.plus(closeDateOffset, ChronoUnit.DAYS));
			if (workOrder.getDateClosed().isBefore(nowLocalDate)) {
				workOrder.workOrderStatus().setEnumValue(WorkOrderStatusEnum.done);
			} else {
				workOrder.workOrderStatus().setEnumValue(WorkOrderStatusEnum.pending);
			}
			workOrder.setCode("WO-" + ticket.getCode() + "-" + i);
			workOrder.setName("Work Order n°" + workOrder.getCode());
			workOrder.setTicketId(ticket.getTicketId());
			workOrder.setTicketCode(ticket.getCode());
			workOrder.setDescription("Cette opération de maintenance concerne le ticket " + ticket.getCode() + ". Elle a été créée le " + dateCreated + ".");
			workOrder.setDueDate(dateCreated.plus(DataGenerator.RND.nextInt(2 * MAX_DUE_DATE_DELAY + 1) - (long) MAX_DUE_DATE_DELAY, ChronoUnit.DAYS));
			workOrderDAO.create(workOrder);
		}

	}

}
