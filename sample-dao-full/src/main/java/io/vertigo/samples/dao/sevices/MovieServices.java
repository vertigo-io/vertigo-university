package io.vertigo.samples.dao.sevices;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.MyMovie;

public interface MovieServices extends StoreServices {

	void saveMyMovie(MyMovie myMovie);

	DtList<MyMovie> findMoviesByCriteria1(String title, Integer year);

	DtList<MyMovie> findMoviesByCriteria2(String title, Integer year);

}
