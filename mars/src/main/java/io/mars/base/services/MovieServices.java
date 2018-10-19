package io.mars.base.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.mars.domain.movies.Movie;
import io.vertigo.core.component.Component;

public interface MovieServices extends Component {

	DtList<Movie> getMovies(DtListState dtListState);

	void save(Movie movie);

	Movie get(Long movId);
}
