package io.mars.maintenance.datageneration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.mars.datageneration.DataGenerator;
import io.mars.datageneration.DensityDistribution;
import io.mars.maintenance.dao.TicketDAO;
import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.TicketStatusEnum;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;

public class TicketGenerator implements Component {

	// a placer sur la bonne méthode
	//	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)

	//private static final Instant GenesisDate = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
	private static final int CHUNK_SIZE = 1000;
	private final static LocalDate nowLocalDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toLocalDate();
	private static final int MAX_TICKET_CLOSE_DATE_OFFSET = 10;
	private final DensityDistribution qualityEquipmentDensityDistribution;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private EquipmentDAO equipmentDAO;

	@Inject
	private TicketDAO ticketDAO;

	@Inject
	private WorkOrderGenerator workOrderGenerator;

	@Inject
	public TicketGenerator() {
		qualityEquipmentDensityDistribution = new DensityDistribution();
		qualityEquipmentDensityDistribution.addSegment(0.0, 1.0, 48.0);
		qualityEquipmentDensityDistribution.addSegment(1.0, 7.0, 12.0);
		qualityEquipmentDensityDistribution.addSegment(7.0, 9999.0, 96.0);
	}

	public void generatePastTickets(final Instant instant, final ChronoUnit chronoUnit, final int step) {

		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {

			int nbRecordsFound = CHUNK_SIZE;
			Long previousLastId = 0L;

			while (nbRecordsFound == CHUNK_SIZE) {
				final DtList<Equipment> equipmentList = equipmentDAO.loadEquipmentsByChunk(new Long(CHUNK_SIZE), previousLastId, instant);
				nbRecordsFound = equipmentList.size();
				if (nbRecordsFound > 0) {
					previousLastId = equipmentList.get(nbRecordsFound - 1).getEquipmentId();

					for (final Equipment equipmentItem : equipmentList) {
						createTicketsForEquipment(equipmentItem, instant, chronoUnit, step);
					}
				}
			}

			tx.commit();
		}
	}

	private void createTicketsForEquipment(final Equipment equipmentItem, final Instant instant, final ChronoUnit chronoUnit, final int step) {

		final Instant purchaseDate = ZonedDateTime.of(equipmentItem.getPurchaseDate(), LocalTime.of(0, 0), ZoneOffset.UTC).toInstant();

		if (purchaseDate.isBefore(instant)) {
			final long yearOfEvent = ChronoUnit.YEARS.between(purchaseDate.atZone(ZoneOffset.UTC), instant.atZone(ZoneOffset.UTC));
			final Double density = qualityEquipmentDensityDistribution.getDensity(yearOfEvent);

			final Double realDensity = density / (chronoUnit.getDuration().getSeconds() / ChronoUnit.YEARS.getDuration().getSeconds()) * step;
			final Double tirage = DataGenerator.RND.nextDouble();

			if (realDensity >= tirage) {
				final Ticket ticket = new Ticket();
				final int closeDateOffset = DataGenerator.RND.nextInt(MAX_TICKET_CLOSE_DATE_OFFSET);

				ticket.setDateCreated(LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
				ticket.setDateClosed(ticket.getDateCreated().plus(closeDateOffset, ChronoUnit.DAYS));
				ticket.setCode("T-" + tirage.toString().substring(0, 6));
				ticket.setTitle("Ticket n°" + ticket.getCode());
				ticket.setEquipmentId(equipmentItem.getEquipmentId());
				ticket.setDescription("<p><strong>Ce ticket a été créé le " + ticket.getDateCreated() + "</strong></p><p>Il a été créé pour l'équipement " + equipmentItem.getName() + "</p><p>En cas de non résolution, merci de contacter :</p><ul><li>Votre centre de maintenance MarsAssistance</li><li>Votre supérieur hiérarchique</li></ul>");
				if (ticket.getDateClosed().isBefore(nowLocalDate)) {
					ticket.ticketStatus().setEnumValue(TicketStatusEnum.closed);
				} else {
					ticket.ticketStatus().setEnumValue(TicketStatusEnum.open);
				}
				ticketDAO.create(ticket);
				workOrderGenerator.createWorkOrdersForTicket(ticket, nowLocalDate);

			}
		}
	}

}
