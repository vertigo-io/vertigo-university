package io.mars.hr.datageneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.datageneration.GenerationConfig;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Person;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

public final class PersonGenerator implements Component {

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private PersonDAO personDAO;
	@Inject
	private ResourceManager resourceManager;

	public void createInitialPersonsFromCSV(final String csvFilePath) {
		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == GenerationConfig.PERSON_CSV_FILE_COLUMN_NUMBER, "CSV File Format not suitable for Persons");
				// ---
				final String firstName = nextRecord[0];
				final String lastName = nextRecord[1];
				final String email = nextRecord[2];
				final String tags = nextRecord[3];
				final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				final LocalDate dateHired = LocalDate.parse(nextRecord[4], dateFormatter);

				personDAO.create(createPerson(firstName, lastName, email, tags, dateHired));

			}

			tx.commit();

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private static Person createPerson(final String firstName,
			final String lastName,
			final String eMail,
			final String tags,
			final LocalDate dateHired) {
		final Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(eMail);
		person.setTags(tags);
		person.setDateHired(dateHired);
		return person;
	}

}
