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

import io.mars.fileinfo.FileInfoStd;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Person;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

public final class PersonGenerator implements Component {

	private static final int PERSON_CSV_FILE_COLUMN_NUMBER = 6;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private PersonDAO personDAO;
	@Inject
	private ResourceManager resourceManager;

	@Inject
	private FileManager fileManager;
	@Inject
	private StoreManager storeManager;

	public void createInitialPersonsFromCSV(final String csvFilePath) {
		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream(), "UTF-8"));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == PERSON_CSV_FILE_COLUMN_NUMBER, "CSV File Format not suitable for Persons");
				// ---
				final String firstName = nextRecord[0];
				final String lastName = nextRecord[1];
				final String email = nextRecord[2];
				final String tags = nextRecord[3];
				final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				final LocalDate dateHired = LocalDate.parse(nextRecord[4], dateFormatter);
				final String picturePath = nextRecord[5];

				Long pictureId = null;
				if (!picturePath.isEmpty()) {
					final VFile pictureFile = fileManager.createFile(
							picturePath.substring(picturePath.lastIndexOf('/') + 1),
							"image/" + picturePath.substring(picturePath.lastIndexOf('.') + 1),
							this.getClass().getResource(picturePath));
					final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(pictureFile));
					pictureId = (Long) fileInfo.getURI().getKey();
				}
				personDAO.create(createPerson(firstName, lastName, email, tags, dateHired, pictureId));
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
			final LocalDate dateHired,
			final Long pictureId) {
		final Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(eMail);
		person.setTags(tags);
		person.setDateHired(dateHired);
		person.setPicturefileId(pictureId);
		return person;
	}

}
