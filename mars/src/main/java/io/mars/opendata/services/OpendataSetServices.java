package io.mars.opendata.services;

import javax.inject.Inject;

import io.mars.fileinfo.FileInfoStd;
import io.mars.opendata.dao.OpendataSetDAO;
import io.mars.opendata.domain.OpendataSet;
import io.mars.support.services.MarsFileServices;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;
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
public class OpendataSetServices implements Component, Activeable {

	@Inject
	private OpendataSetDAO opendataSetDAO;
	@Inject
	private StoreManager storeManager;
	@Inject
	private MarsFileServices commonsServices;
	@Inject
	private FileManager fileManager;

	private VFile defaultOpendataSetPicture;

	@Override
	public void start() {

		defaultOpendataSetPicture = fileManager.createFile(
				"defaultOpendataSetPhoto.png",
				"image/png",
				OpendataSetServices.class.getResource("/defaultOpendataSetPhoto.png"));
	}

	@Override
	public void stop() {
		//nothing
	}

	public OpendataSet getOpendataSet(final Long odsId) {
		return opendataSetDAO.get(odsId);
	}

	public void save(final OpendataSet opendataSet) {
		opendataSetDAO.save(opendataSet);
	}

	public void updateOpendataSet(final OpendataSet opendataSet) {
		opendataSetDAO.update(opendataSet);
	}

	public DtList<OpendataSet> getOpendataSets(final DtListState dtListState) {
		return opendataSetDAO.findAll(Criterions.alwaysTrue(), dtListState);
	}

	public VFile getOpendataSetPicture(final Long fileId) {
		//apply security check
		if (fileId == null) {
			return defaultOpendataSetPicture;
		}
		return storeManager.getFileStore().read(toFileInfoStdURI(fileId)).getVFile();
	}

	public void saveOpendataSetPicture(final Long odsId, final FileInfoURI odsPictureTmp) {
		final OpendataSet opendataSet = getOpendataSet(odsId);
		//apply security check
		final Long oldPicture = opendataSet.getPicturefileId();
		final VFile fileTmp = commonsServices.getFileTmp(odsPictureTmp);
		final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoStd(fileTmp));
		opendataSet.setPicturefileId((Long) fileInfo.getURI().getKey());
		updateOpendataSet(opendataSet);
		if (oldPicture != null) {
			storeManager.getFileStore().delete(toFileInfoStdURI(oldPicture));
		}
	}

	private static FileInfoURI toFileInfoStdURI(final Long fileId) {
		return new FileInfoURI(FileInfoDefinition.findFileInfoDefinition(FileInfoStd.class), fileId);
	}

	public OpendataSet createOpendataSet(final OpendataSet opendataSet) {
		return opendataSetDAO.create(opendataSet);
	}

}
