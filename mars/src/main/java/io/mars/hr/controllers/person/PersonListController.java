package io.mars.hr.controllers.person;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.domain.DtDefinitions.PersonFields;
import io.mars.hr.domain.Person;
import io.mars.hr.services.person.PersonServices;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/hr/persons")
public class PersonListController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Person> persons = ViewContextKey.of("persons");
	private static final ViewContextKey<String> listRenderer = ViewContextKey.of("listRenderer");

	@Inject
	private PersonServices personServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext, @RequestParam("renderer") final Optional<String> renderer) {
		viewContext.publishRef(listRenderer, renderer.orElse("table"));
		final DtListState dtListState = DtListState.of(200, 0, PersonFields.lastName.name(), false);
		viewContext.publishDtList(persons, personServices.getPersons(dtListState));
	}

	@PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
		viewContext.publishDtList(persons, personServices.getPersons(dtListState));
		return viewContext;
	}
}
