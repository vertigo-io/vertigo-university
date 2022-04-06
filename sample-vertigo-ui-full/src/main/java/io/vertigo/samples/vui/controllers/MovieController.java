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

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.samples.vui.domain.Actor;
import io.vertigo.samples.vui.domain.Country;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.Role;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.vega.webservice.model.UiObject;
import io.vertigo.vega.webservice.validation.DefaultDtObjectValidator;
import io.vertigo.vega.webservice.validation.UiMessageStack;

@Controller
@RequestMapping("/movie")
public class MovieController extends AbstractVSpringMvcController {

	private final ViewContextKey<Movie> movieKey = ViewContextKey.of("movie");
	private final ViewContextKey<Role> rolesKey = ViewContextKey.of("roles");
	private final ViewContextKey<Actor> actorsKey = ViewContextKey.of("actors");
	private final ViewContextKey<Country> countriesKey = ViewContextKey.of("countries");

	@Inject
	private MovieServices movieServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishDto(movieKey, new Movie());
		viewContext.publishMdl(countriesKey, Country.class, null);
		toModeCreate();
	}

	@GetMapping("/{movId}")
	public void initContext(final ViewContext viewContext, @PathVariable("movId") final Long movId) {
		final Movie movie = movieServices.loadMovieWithRoles(movId);
		viewContext.publishDto(movieKey, movie);
		viewContext.publishDtList(rolesKey, movie.role().get());
		viewContext.publishDtList(actorsKey, movieServices.getActorsByMovie(movId));
		viewContext.publishMdl(countriesKey, Country.class, null);
	}

	@PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, @ViewAttribute("roles") final DtList<Role> roles, final DtListState dtListState) {
		//Long movId = viewContext.getUiList(actorsKey).;

		viewContext.publishDtList(rolesKey, movieServices.sortRoles(roles, dtListState));
		return viewContext;
		//.publishDtList(bases, baseServices.getBases(dtListState));
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_validate")
	public ViewContext doValidate(final ViewContext viewContext, @ViewAttribute("movie") final UiObject<Movie> movieUi, final UiMessageStack uiMessageStack) {
		//could use more specialized validator
		final Movie movie = movieUi.mergeAndCheckInput(List.of(new DefaultDtObjectValidator<>()), uiMessageStack);
		movieServices.validate(movie, uiMessageStack);
		return viewContext;
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("movie") final Movie movie) {
		movieServices.save(movie);
		return "redirect:/movie/" + movie.getMovId();
	}

}
