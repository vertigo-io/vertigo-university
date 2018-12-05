package io.mars.datageneration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import io.mars.basemanagement.datageneration.BaseGenerator;
import io.mars.basemanagement.datageneration.EquipmentGenerator;
import io.mars.basemanagement.datageneration.ReferenceDataGenerator;
import io.mars.hr.datageneration.PersonGenerator;
import io.mars.maintenance.datageneration.TicketGenerator;
import io.vertigo.core.component.Component;
import io.vertigo.lang.Assertion;

public class DataGenerator implements Component {

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

	public void generateInitialEquipments() {
		equipmentGenerator.createInitialEquipmentCategories();
		equipmentGenerator.createInitialEquipmentTypesFromCSV("initdata/equipmentTypes.csv");
		equipmentGenerator.createInitialEquipments();

	}

	public void generateInitialPersons() {
		personGenerator.createInitialPersonsFromCSV("initdata/persons.csv");
	}

	public void generateInitialBases() {
		baseGenerator.generateInitialBases();
	}

	public void generateReferenceData() {
		referenceDataGenerator.generateReferenceData();
	}

	public void generatePastData(final Instant from, final Instant to) {
		Assertion.checkNotNull(from);
		Assertion.checkNotNull(to);
		//
		Instant timeCursor = from;
		while (timeCursor.isBefore(to)) {
			ticketGenerator.generatePastTickets(timeCursor, ChronoUnit.DAYS, 1);
			// tous les 10 à 20 jours
			timeCursor = timeCursor.plus(GenerationConfig.rnd.nextInt(10) + 10, ChronoUnit.DAYS);
		}
	}

}
