package io.mars.basemanagement.services.base;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.dao.GeosectorDAO;
import io.mars.basemanagement.dao.PictureDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseOverview;
import io.mars.basemanagement.domain.BasesSummary;
import io.mars.basemanagement.domain.Geosector;
import io.mars.basemanagement.domain.Picture;
import io.mars.basemanagement.search.BaseIndex;
import io.mars.domain.DtDefinitions.PictureFields;
import io.mars.fileinfo.FileInfoStd;
import io.mars.hr.services.person.PersonServices;
import io.mars.support.services.MarsFileServices;
import io.vertigo.account.account.Account;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.metamodel.FileInfoDefinition;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.social.services.notification.Notification;
import io.vertigo.social.services.notification.NotificationServices;

@Transactional
public class BaseServices implements Component, Activeable {

	@Inject
	private BaseDAO baseDAO;
	@Inject
	private PictureDAO pictureDAO;
	@Inject
	private GeosectorDAO geosectorDAO;

	@Inject
	private BasemanagementPAO basemanagementPAO;

	@Inject
	private MarsFileServices commonsServices;
	@Inject
	private FileManager fileManager;
	@Inject
	private StoreManager storeManager;

	@Inject
	private PersonServices personServices;
	@Inject
	private NotificationServices notificationServices;

	private VFile defaultPhoto;

	@Override
	public void start() {
		defaultPhoto = fileManager.createFile(
				"defaultBase.png",
				"image/png",
				PersonServices.class.getResource("/defaultBase.png"));
	}

	@Override
	public void stop() {
		//rien
	}

	public Base get(final Long baseId) {
		return baseDAO.get(baseId);
	}

	public BaseOverview getBaseOverview(final Long baseId) {
		return basemanagementPAO.getBaseOverview(baseId);
	}

	public void save(final Base base, final List<FileInfoURI> addedPictureFile, final DtList<Picture> deletedPictures) {
		//apply Security Checks

		//update Base
		baseDAO.save(base);

		//remove deleted pictures
		for (final Picture deletedPicture : deletedPictures) {
			pictureDAO.delete(deletedPicture.getPictureId());
			storeManager.getFileStore().delete(toFileInfoStdURI(deletedPicture.getPicturefileId()));
		}
		//create added pictures
		for (final FileInfoURI fileInfoURI : addedPictureFile) {
			final VFile fileTmp = commonsServices.getFileTmp(fileInfoURI);
			final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(fileTmp));

			final Picture picture = new Picture();
			picture.setBaseId(base.getBaseId());
			picture.setPicturefileId((Long) fileInfo.getURI().getKey());
			pictureDAO.create(picture);
		}

		final Notification notification = Notification.builder()
				.withSender("System")
				.withTitle("Base updated")
				.withContent("Base " + base.getCode() + " informations updated")
				.withTTLInSeconds(600)
				.withType("MARS-BASE") //should prefix by app, in case of multi-apps notifications
				.withTargetUrl("/mars/basemanagement/base/information/" + base.getBaseId())
				.build();
		sendNotificationToAll(notification);
	}

	public DtList<Base> getBases(final DtListState dtListState) {
		final DtList<Base> bases = baseDAO.findAll(Criterions.alwaysTrue(), dtListState);
		return bases;
	}

	public DtList<Geosector> getAllGeosectors() {
		return geosectorDAO.findAll(Criterions.alwaysTrue(), DtListState.defaultOf(Geosector.class));
	}

	public DtList<BaseIndex> getBaseIndex(final List<Long> baseIds) {
		return basemanagementPAO.loadBaseIndex(baseIds);
	}

	public BasesSummary getBaseSummary() {
		return basemanagementPAO.getBasesSummary();
	}

	public VFile getBaseMainPicture(final Long baseId) {
		final DtList<Picture> pictures = getPictures(baseId);
		if (pictures.isEmpty()) {
			return defaultPhoto;
		}
		return getBasePicture(pictures.get(0).getPicturefileId());
	}

	public VFile getBasePicture(final Long fileId) {
		//apply security check
		if (fileId == null) {
			return defaultPhoto;
		}
		return storeManager.getFileStore().read(toFileInfoStdURI(fileId)).getVFile();
	}

	private static FileInfoURI toFileInfoStdURI(final Long fileId) {
		return new FileInfoURI(FileInfoDefinition.findFileInfoDefinition(FileInfoStd.class), fileId);
	}

	public DtList<Picture> getPictures(final Long baseId) {
		return pictureDAO.findAll(Criterions.isEqualTo(PictureFields.baseId, baseId), DtListState.defaultOf(Picture.class));
	}

	private void sendNotificationToAll(final Notification notification) {
		final Set<UID<Account>> accountUIDs = personServices.getPersons(DtListState.of(null))
				.stream()
				.map((person) -> UID.of(Account.class, String.valueOf(person.getPersonId())))
				.collect(Collectors.toSet());
		notificationServices.send(notification, accountUIDs);
	}
}
