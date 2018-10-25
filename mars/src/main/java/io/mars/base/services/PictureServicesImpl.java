package io.mars.base.services;

import javax.inject.Inject;

import io.mars.base.dao.PictureDAO;
import io.mars.base.domain.Picture;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class PictureServicesImpl implements PictureServices {

	@Inject
	private PictureDAO pictureDAO;

	@Override
	public Picture get(final Long pictureId) {
		return pictureDAO.get(pictureId);
	}

	@Override
	public void save(final Picture picture) {
		pictureDAO.save(picture);
	}

	@Override
	public DtList<Picture> getPictures(final DtListState dtListState) {
		return pictureDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
