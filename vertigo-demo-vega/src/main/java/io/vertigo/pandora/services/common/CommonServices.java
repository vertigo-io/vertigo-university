package io.vertigo.pandora.services.common;

import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Component;

public interface CommonServices extends Component {

	FacetedQueryResult<DtObject, SearchQuery> searchAll(String criteria, DtListState dtListState);

	long reloadAll();

	long reindexAll();
}
