package io.vertigo.pandora.webservices.common;

import javax.inject.Inject;

import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.impl.authentication.UsernameAuthenticationToken;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Assertion;
import io.vertigo.pandora.services.common.CommonServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@PathPrefix("/common")
public class CommonWebServices implements WebServices {

	@Inject
	private AuthenticationManager authenticationManager;

	@Inject
	private CommonServices commonServices;

	@AnonymousAccessAllowed
	@GET("/search")
	public FacetedQueryResult<? extends DtObject, SearchQuery> searchMovies(@QueryParam("q") final String criteria, final DtListState dtListState) {
		return search(criteria, dtListState);
	}

	@AnonymousAccessAllowed
	@POST("/search")
	public FacetedQueryResult search(@InnerBodyParam("criteria") final String criteria, final DtListState dtListState) {
		Assertion.checkArgument(dtListState.getSkipRows() == 0, "La recherche sur scope ALL, ne support pas la pagination");
		Assertion.checkArgument(dtListState.getSortFieldName() == null, "La recherche sur scope ALL, ne support pas le tri ({0})", dtListState.getSortFieldName());
		return commonServices.searchAll(criteria, dtListState);
	}

	@AnonymousAccessAllowed
	@GET("/index")
	public long indexAll() {
		return commonServices.reindexAll();
	}

	@AnonymousAccessAllowed
	@GET("/reload")
	public long reloadAll() {
		return commonServices.reloadAll();
	}

	@AnonymousAccessAllowed
	@GET("/login")
	public void login() {
		authenticationManager.login(new UsernameAuthenticationToken("Test"));
	}

}
