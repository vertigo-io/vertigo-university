package io.mars.humanresources.controllers.person;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.humanresources.domain.Person;
import io.mars.humanresources.services.person.PersonServices;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/humanresources/person/list")
public class PersonListController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Person> persons = ViewContextKey.of("persons");

	@Inject
	private PersonServices personServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		final DtListState dtListState = new DtListState(200, 0, null, null);
		viewContext.publishDtList(persons, personServices.getPersons(dtListState));
	}

	@PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
		viewContext.publishDtList(persons, personServices.getPersons(dtListState));
		return viewContext;
	}

}
