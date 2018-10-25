package io.mars.base.services;

import io.mars.base.domain.Picture;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface PictureServices extends Component {

	DtList<Picture> getPictures(DtListState dtListState);

	void save(Picture picture);

	Picture get(Long pictureId);
	
	//DtList<Picture> getPicturesFromBase(Long baseId);
}
