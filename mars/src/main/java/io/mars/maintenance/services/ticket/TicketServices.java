package io.mars.maintenance.services.ticket;

import javax.inject.Inject;

import io.mars.maintenance.dao.TicketDAO;
import io.mars.maintenance.domain.Ticket;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class TicketServices implements Component{


	@Inject
	private TicketDAO ticketDAO;

	public Ticket getTicketFromId(final Long baseId) {
		return ticketDAO.get(baseId);
	}

	public void save(final Ticket ticket) {
		ticketDAO.save(ticket);
	}

	public DtList<Ticket> getTickets(final DtListState dtListState) {
		return ticketDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}

}


