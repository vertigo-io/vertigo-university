package io.mars.datageneration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.mars.maintenance.dao.TicketDAO;
import io.mars.maintenance.domain.Ticket;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;

public class DataGenerator implements Component {

	// a placer sur la bonne méthode
	//	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)

	private static final Long RANDOM_SEED = 1337L;
	private static final Random rnd = new Random(RANDOM_SEED);
	private static final Logger logger = LogManager.getLogger(DataGenerator.class);
	//private static final Instant GenesisDate = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
	private static final int CHUNK_SIZE = 1000;

	private final DensityDistribution qualityEquipmentDensityDistribution;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	public DataGenerator() {
		qualityEquipmentDensityDistribution = new DensityDistribution();
		qualityEquipmentDensityDistribution.addSegment(0.0, 1.0, 48.0);
		qualityEquipmentDensityDistribution.addSegment(1.0, 7.0, 12.0);
		qualityEquipmentDensityDistribution.addSegment(7.0, 9999.0, 96.0);
	}

	@Inject
	private EquipmentDAO equipmentDAO;

	@Inject
	private TicketDAO ticketDAO;

	//@Inject
	//private WorkOrderDAO workOrderDAO;

	public void generatePastData(final Instant from, final Instant to) {
		Assertion.checkNotNull(from);
		Assertion.checkNotNull(to);
		//
		Instant timeCursor = from;
		while (timeCursor.isBefore(to)) {
			generatePastTickets(timeCursor, ChronoUnit.DAYS, 1);
			// tous les 10 à 20 jours
			timeCursor = timeCursor.plus(rnd.nextInt(10) + 10, ChronoUnit.DAYS);
		}
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
			final Double density = qualityEquipmentDensityDistribution.getDensity(Double.valueOf(yearOfEvent));

			//final Double realDensity = density / 365 * step;

			final Long essai = ChronoUnit.YEARS.getDuration().getSeconds() / chronoUnit.getDuration().getSeconds();
			logger.info("######## Valeur du ratio : " + essai);
			final Double realDensity = density / (chronoUnit.getDuration().getSeconds() / ChronoUnit.YEARS.getDuration().getSeconds()) * step;
			final Double tirage = rnd.nextDouble();
			if (realDensity >= tirage) {
				final Ticket ticket = new Ticket();
				ticket.setDateCreated(LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
				ticket.setCode("T-" + tirage.toString().substring(4));
				ticket.setTitle("Ticket n°" + ticket.getCode());
				ticket.setEquipmentId(equipmentItem.getEquipmentId());
				ticketDAO.create(ticket);
			}
		}
	}

}
