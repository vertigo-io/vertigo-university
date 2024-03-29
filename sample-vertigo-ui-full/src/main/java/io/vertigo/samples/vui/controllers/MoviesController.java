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

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/movies")
public class MoviesController extends AbstractVSpringMvcController {

	private final ViewContextKey<Movie> moviesKey = ViewContextKey.of("movies");

	@Inject
	private MovieServices movieServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishDtList(moviesKey, movieServices.getMovies(DtListState.defaultOf(Movie.class)));
	}

	@PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
		viewContext.publishDtList(moviesKey, movieServices.getMovies(dtListState));
		return viewContext;
	}
}
