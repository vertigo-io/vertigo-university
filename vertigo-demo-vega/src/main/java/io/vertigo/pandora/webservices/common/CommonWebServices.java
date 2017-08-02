package io.vertigo.pandora.webservices.common;

import java.util.Arrays;

import javax.inject.Inject;

import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.identity.Account;
import io.vertigo.account.identity.IdentityManager;
import io.vertigo.account.impl.authentication.UsernameAuthenticationToken;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Assertion;
import io.vertigo.pandora.services.common.CommonServices;
import io.vertigo.persona.security.VSecurityManager;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.model.UiListState;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@PathPrefix("/common")
public class CommonWebServices implements WebServices {

	@Inject
	private IdentityManager identityManager;
	@Inject
	private AuthenticationManager authenticationManager;
	@Inject
	private VSecurityManager securityManager;

	@Inject
	private CommonServices commonServices;

	@AnonymousAccessAllowed
	@GET("/search")
	public FacetedQueryResult<? extends DtObject, SearchQuery> searchMovies(@QueryParam("q") final String criteria, final UiListState uiListState) {
		return search(criteria, uiListState);
	}

	@AnonymousAccessAllowed
	@POST("/search")
	public FacetedQueryResult search(@InnerBodyParam("criteria") final String criteria, final UiListState uiListState) {
		Assertion.checkArgument(uiListState.getSkip() == 0, "La recherche sur scope ALL, ne support pas la pagination");
		Assertion.checkArgument(uiListState.getSortFieldName() == null, "La recherche sur scope ALL, ne support pas le tri ({0})", uiListState.getSortFieldName());
		return commonServices.searchAll(criteria, uiListState.toDtListState());
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
		if (identityManager.getStore().getAccountsCount() == 0) {
			final Account testAccount0 = Account.builder("0").withDisplayName("John doe").withEmail("john.doe@yopmail.com").build();
			identityManager.getStore().saveAccounts(Arrays.asList(testAccount0));

			authenticationManager.login(new UsernameAuthenticationToken(testAccount0.getURI().toString()));
			securityManager.getCurrentUserSession().get().authenticate();
		} else {
			authenticationManager.login(new UsernameAuthenticationToken(DtObjectUtil.createURI(Account.class, "0").toString()));
			securityManager.getCurrentUserSession().get().authenticate();
		}
	}

}
