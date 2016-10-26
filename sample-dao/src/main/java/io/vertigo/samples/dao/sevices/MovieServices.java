package io.vertigo.samples.dao.sevices;

import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.MyMovie;

public interface MovieServices extends StoreServices {

	void saveMyMovie(MyMovie myMovie);

}
