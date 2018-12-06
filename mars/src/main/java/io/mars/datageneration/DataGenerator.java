package io.mars.datageneration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import io.mars.basemanagement.datageneration.BaseGenerator;
import io.mars.basemanagement.datageneration.EquipmentGenerator;
import io.mars.basemanagement.datageneration.ReferenceDataGenerator;
import io.mars.hr.datageneration.PersonGenerator;
import io.mars.maintenance.datageneration.TicketGenerator;
import io.vertigo.core.component.Component;
import io.vertigo.lang.Assertion;

public class DataGenerator implements Component {

	private static final Long RANDOM_SEED = 1337L;
	public static final Random RND = new Random(RANDOM_SEED);

	// a placer sur la bonne méthode
	//	@DaemonScheduled(name = "DMN_TICKET_AND_WO_GENERATOR", periodInSeconds = 10)

	@Inject
	private TicketGenerator ticketGenerator;
	@Inject
	private EquipmentGenerator equipmentGenerator;
	@Inject
	private PersonGenerator personGenerator;
	@Inject
	private BaseGenerator baseGenerator;
	@Inject
	private ReferenceDataGenerator referenceDataGenerator;

	private final int initialEquipmentUnits;

	@Inject
	public DataGenerator(
			@Named("initialEquipmentUnits") final Integer initialEquipmentUnits) {
		Assertion.checkNotNull(initialEquipmentUnits);
		//---
		this.initialEquipmentUnits = initialEquipmentUnits;
	}

	public void generateInitialData() {
		generateReferenceData();
		generateInitialPersons();
		generateInitialBases();
		generateInitialEquipments();
		generatePastData(ZonedDateTime.of(LocalDate.of(2017, 1, 1), LocalTime.of(0, 0), ZoneOffset.UTC).toInstant(), Instant.now());
	}

	private void generateInitialEquipments() {
		equipmentGenerator.createInitialEquipmentCategories();
		equipmentGenerator.createInitialEquipmentTypesFromCSV("initdata/equipmentTypes.csv");
		equipmentGenerator.createInitialEquipments(initialEquipmentUnits);
	}

	private void generateInitialPersons() {
		personGenerator.createInitialPersonsFromCSV("initdata/persons.csv");
	}

	private void generateInitialBases() {
		baseGenerator.generateInitialBases();
	}

	private void generateReferenceData() {
		referenceDataGenerator.generateReferenceData();
	}

	private void generatePastData(final Instant from, final Instant to) {
		Assertion.checkNotNull(from);
		Assertion.checkNotNull(to);
		//
		Instant timeCursor = from;
		while (timeCursor.isBefore(to)) {
			ticketGenerator.generatePastTickets(timeCursor, ChronoUnit.DAYS, 1);
			// tous les 10 à 20 jours
			timeCursor = timeCursor.plus(RND.nextInt(10) + 10, ChronoUnit.DAYS);
		}
	}

}
