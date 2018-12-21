package io.mars.maintenance.services.ticket;

import java.time.LocalDate;

import javax.inject.Inject;

import io.mars.domain.DtDefinitions.TicketFields;
import io.mars.maintenance.dao.TicketDAO;
import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.TicketStatusEnum;
import io.vertigo.commons.eventbus.EventBusManager;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.lang.Assertion;
import io.vertigo.ledger.services.LedgerManager;

@Transactional
public class TicketServices implements Component {

	@Inject
	private TicketDAO ticketDAO;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private EventBusManager eventBusManager;
	@Inject
	private LedgerManager ledgerManager;

	public Ticket getTicketFromId(final Long ticketId) {
		return ticketDAO.get(ticketId);
	}

	public void save(final Ticket ticket) {
		ticketDAO.save(ticket);
	}

	public void createTicket(final Ticket ticket) {
		ticket.setDateCreated(LocalDate.now());
		ticket.ticketStatus().setEnumValue(TicketStatusEnum.open);
		//---
		ticketDAO.save(ticket);
		transactionManager.getCurrentTransaction()
				.addAfterCompletion(txCommited -> {
					if (txCommited) {
						eventBusManager.post(new TicketEvent(TicketEvent.Type.CREATE, ticket));
					}
				});
	}

	public DtList<Ticket> getTickets(final DtListState dtListState) {
		return ticketDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}

	public DtList<Ticket> getLastestTicketsByBase(final Long baseId) {
		Assertion.checkNotNull(baseId);
		//---
		return ticketDAO.getLastTicketsByBaseId(baseId);
	}

	public DtList<Ticket> getLastestTickets() {
		return ticketDAO.getLastTickets();
	}

	public DtList<Ticket> getOpenedTicketsByEquipment(final Long equipmentId) {
		Assertion.checkNotNull(equipmentId);
		//---
		return ticketDAO.findAll(
				Criterions.isEqualTo(TicketFields.EQUIPMENT_ID, equipmentId)
						.and(Criterions.isNotEqualTo(TicketFields.TICKET_STATUS_ID, (String) TicketStatusEnum.closed.getEntityUID().getId())),
				Integer.MAX_VALUE);
	}

	public DtList<Ticket> getClosedTicketsByEquipment(final Long equipmentId) {
		Assertion.checkNotNull(equipmentId);
		//---
		return ticketDAO.findAll(
				Criterions.isEqualTo(TicketFields.EQUIPMENT_ID, equipmentId)
						.and(Criterions.isEqualTo(TicketFields.TICKET_STATUS_ID, (String) TicketStatusEnum.closed.getEntityUID().getId())),
				Integer.MAX_VALUE);
	}
	
	@EventBusSubscribed
	public void onTicketEvent(final TicketEvent ticketEvent) {
		if (ticketEvent.getType() == TicketEvent.Type.CREATE) {
			Ticket ticket = ticketEvent.getTicket();
			StringBuilder sbSerializedTicket = new StringBuilder();
			sbSerializedTicket.append("Création du ticket :")
							  .append(ticket.getCode())
			 				  .append(".")
			 				  .append(ticket.getTitle())
			 				  .append(". Ticket créé le ")
			 				  .append(ticket.getDateCreated());
			
			ledgerManager.sendData(sbSerializedTicket.toString());
		}
	}

}
