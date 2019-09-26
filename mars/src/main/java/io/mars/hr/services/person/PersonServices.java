package io.mars.hr.services.person;

import javax.inject.Inject;

import io.mars.fileinfo.FileInfoStd;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Person;
import io.mars.support.services.MarsFileServices;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.metamodel.FileInfoDefinition;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;

@Transactional
public class PersonServices implements Component, Activeable {

	@Inject
	private PersonDAO personDAO;

	@Inject
	private StoreManager storeManager;

	@Inject
	private FileManager fileManager;

	@Inject
	private MarsFileServices commonsServices;

	@Inject
	private CollectionsManager collectionsManager;

	private VFile defaultPhoto;

	@Override
	public void start() {
		defaultPhoto = fileManager.createFile(
				"defaultPhoto.png",
				"image/png",
				PersonServices.class.getResource("/defaultPhoto.png"));
	}

	@Override
	public void stop() {
		//rien
	}

	public Person getPerson(final Long personId) {
		return personDAO.get(personId);
	}

	public void updatePerson(final Person person) {
		personDAO.update(person);
	}

	public Person createPerson(final Person person) {
		return personDAO.create(person);
	}

	public Person initPerson() {
		return new Person();
	}

	public DtList<Person> getPersons(final DtListState dtListState) {
		final DtList<Person> persons = personDAO.findAll(Criterions.alwaysTrue(), dtListState);
		if (dtListState.getSortFieldName().isPresent()) {
			return collectionsManager.sort(persons, dtListState.getSortFieldName().get(), dtListState.isSortDesc().get());
		}
		return persons;
	}

	public VFile getPersonPicture(final Long fileId) {
		//apply security check
		if (fileId == null) {
			return defaultPhoto;
		}
		return storeManager.getFileStore().read(toFileInfoStdURI(fileId)).getVFile();
	}

	public void savePersonPicture(final Long personId, final FileInfoURI personPictureTmp) {
		final Person person = getPerson(personId);
		//apply security check
		final Long oldPicture = person.getPicturefileId();
		final VFile fileTmp = commonsServices.getFileTmp(personPictureTmp);
		final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(fileTmp));
		person.setPicturefileId((Long) fileInfo.getURI().getKey());
		updatePerson(person);
		if (oldPicture != null) {
			storeManager.getFileStore().delete(toFileInfoStdURI(oldPicture));
		}
	}

	private static FileInfoURI toFileInfoStdURI(final Long fileId) {
		return new FileInfoURI(FileInfoDefinition.findFileInfoDefinition(FileInfoStd.class), fileId);
	}
}
