/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.samples.vui.controllers;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.model.SearchQuery;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.samples.vui.domain.DtDefinitions.MovieIndexFields;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.MovieIndex;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/moviesSearch")
public class MoviesSearchController extends AbstractVSpringMvcController {

	private static final ViewContextKey<String> listRenderer = ViewContextKey.of("listRenderer");
	private static final ViewContextKey<String> criteriaKey = ViewContextKey.of("criteria");
	private static final ViewContextKey<FacetedQueryResult<MovieIndex, SearchQuery>> moviesKey = ViewContextKey.of("movies");

	@Inject
	private MovieServices movieServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext, @RequestParam("renderer") final Optional<String> renderer) {
		viewContext.publishRef(criteriaKey, "");
		viewContext.publishRef(listRenderer, renderer.orElse("table"));
		final FacetedQueryResult<MovieIndex, SearchQuery> facetedQueryResult = movieServices.searchMovies("", SelectedFacetValues.empty().build(), DtListState.defaultOf(Movie.class));
		viewContext.publishFacetedQueryResult(moviesKey, MovieIndexFields.movId, facetedQueryResult, criteriaKey);

	}

	@PostMapping("/_search")
	public ViewContext doSearch(
			final ViewContext viewContext,
			@ViewAttribute("criteria") final String criteria,
			@ViewAttribute("movies") final SelectedFacetValues selectedFacetValues,
			final DtListState dtListState) {
		final FacetedQueryResult<MovieIndex, SearchQuery> facetedQueryResult = movieServices.searchMovies(criteria, selectedFacetValues, dtListState);
		viewContext.publishFacetedQueryResult(moviesKey, MovieIndexFields.movId, facetedQueryResult, criteriaKey);
		return viewContext;
	}

	@GetMapping("/_reindex")
	public ViewContext indexMovies(final ViewContext viewContext) {
		movieServices.indexMovies();
		return viewContext;
	}
}
