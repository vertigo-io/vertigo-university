package io.mars.humanresources.controllers.person;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.mars.humanresources.domain.Person;
import io.mars.humanresources.services.person.PersonServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/humanresources/person/detail")
public class PersonDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Person> personKey = ViewContextKey.of("person");

	@Inject
	private PersonServices personServices;

	@GetMapping("/")
	public void initContext(
			final ViewContext viewContext,
			@RequestParam("personId") final Optional<Long> personIdOpt) {
		if (personIdOpt.isPresent()) {
			viewContext.publishDto(personKey, personServices.getPerson(personIdOpt.get()));
			toModeReadOnly();
		} else {
			viewContext.publishDto(personKey, personServices.initPerson());
			toModeCreate();
		}
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_create")
	public String doCreate(
			@ViewAttribute("person") final Person person,
			final RedirectAttributes redirectAttributes) {
		personServices.createPerson(person);
		redirectAttributes.addAttribute("personId", person.getPersonId());
		return "redirect:/humanresources/person/detail/";
	}

	@PostMapping("/_save")
	public String doSave(
			@ViewAttribute("person") final Person person,
			final RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("personId", person.getPersonId());
		return "redirect:/humanresources/person/detail/";
	}

}
