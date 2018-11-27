package io.mars.datageneration;

import java.time.Instant;
import java.time.LocalDate;
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
import io.vertigo.core.component.Component;
import io.vertigo.lang.Assertion;

public class DataGenerator implements Component {

	// a placer sur la bonne méthode
	//	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)

	private static final Long RANDOM_SEED = 1337L;
	private static final Random rnd = new Random(RANDOM_SEED);
	private static final Logger logger = LogManager.getLogger(DataGenerator.class);
	private static final Instant GenesisDate = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();

	private final DensityDistribution qualityEquipmentDensityDistribution;
	private static long totalSteps = 0;
	private static long createdEquipements = 0;

	@Inject
	public DataGenerator() {
		qualityEquipmentDensityDistribution = new DensityDistribution();
		qualityEquipmentDensityDistribution.addSegment(0.0, 1.0, 48.0);
		qualityEquipmentDensityDistribution.addSegment(1.0, 7.0, 12.0);
		qualityEquipmentDensityDistribution.addSegment(7.0, 9999.0, 96.0);
	}

	public static void main(final String[] args) {
		final DataGenerator dataGenerator = new DataGenerator();
		final Instant dateTest = ZonedDateTime.of(LocalDate.of(2003, 5, 14), LocalTime.of(0, 0), ZoneOffset.UTC).toInstant();
		dataGenerator.generatePastData(dateTest.atZone(ZoneOffset.UTC).plus(1, ChronoUnit.YEARS).toInstant(), dateTest.atZone(ZoneOffset.UTC).plus(7, ChronoUnit.YEARS).toInstant());
		logger.info("totalSteps : " + totalSteps);
		logger.info("createdEquipements : " + createdEquipements);
		logger.info("rate : " + Double.valueOf(createdEquipements) / Double.valueOf(totalSteps));

	}

	@Inject
	private EquipmentDAO equipmentDAO;

	//@Inject
	//private WorkOrderDAO workOrderDAO;

	public void generatePastData(final Instant from, final Instant to) {
		Assertion.checkNotNull(from);
		Assertion.checkNotNull(to);
		// Pas : tous les jours
		Instant timeCursor = from;
		while (timeCursor.isBefore(to)) {
			totalSteps++;
			generatePastTickets(timeCursor, ChronoUnit.DAYS, 1);
			timeCursor = timeCursor.plus(1, ChronoUnit.DAYS);
		}
	}

	public void generatePastTickets(final Instant instant, final ChronoUnit chronoUnit, final int step) {
		//final Equipment equipment = new Equipment();
		//equipment.setPurchaseDate(LocalDate.of(2003, 5, 14));

		final Instant purchaseDate = ZonedDateTime.of(LocalDate.of(2003, 5, 14), LocalTime.of(0, 0), ZoneOffset.UTC).toInstant();

		if (purchaseDate.isBefore(instant)) {
			final long yearOfEvent = ChronoUnit.YEARS.between(purchaseDate.atZone(ZoneOffset.UTC), instant.atZone(ZoneOffset.UTC));
			final Double density = qualityEquipmentDensityDistribution.getDensity(Double.valueOf(yearOfEvent));
			final Double realDensity = density / 365 * step;
			final Double tirage = rnd.nextDouble();
			if (realDensity >= tirage) {
				//final Ticket ticket = new Ticket();
				//ticket.setDateCreated(LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
				logger.info("Ticket created with date" + LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
				createdEquipements++;
			} else {
				logger.info("No ticket for date :" + LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
			}
		} else {
			logger.info("equipement not yet created at " + LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
		}

	}

	public void generateTicketsAndWorkOrders() {
		logger.info(rnd.nextDouble());
	}

}
