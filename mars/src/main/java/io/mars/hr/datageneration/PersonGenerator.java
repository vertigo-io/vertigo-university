package io.mars.hr.datageneration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import io.mars.fileinfo.FileInfoStd;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Person;
import io.mars.util.CSVReaderUtil;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;

@Transactional
public class PersonGenerator implements Component {

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
		CSVReaderUtil.parseCSV(resourceManager, csvFilePath, this::consume);
	}

	private void consume(String csvFilePath, String[] record) {
		Assertion.checkArgument(record.length == PERSON_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Persons", csvFilePath);
		// ---
		final String firstName = record[0];
		final String lastName = record[1];
		final String email = record[2];
		final String tags = record[3];
		final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		final LocalDate dateHired = LocalDate.parse(record[4], dateFormatter);
		final String picturePath = record[5];
		final Long pictureId;

		if (picturePath.isEmpty()) {
			pictureId = null;
		} else {
			final VFile pictureFile = fileManager.createFile(
					picturePath.substring(picturePath.lastIndexOf('/') + 1),
					"image/" + picturePath.substring(picturePath.lastIndexOf('.') + 1),
					this.getClass().getResource(picturePath));
			final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(pictureFile));
			pictureId = (Long) fileInfo.getURI().getKey();
		}

		personDAO.create(createPerson(firstName, lastName, email, tags, dateHired, pictureId));
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
