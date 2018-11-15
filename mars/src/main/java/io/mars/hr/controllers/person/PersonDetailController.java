package io.mars.hr.controllers.person;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.hr.domain.Person;
import io.mars.hr.services.person.PersonServices;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/hr/person")
public class PersonDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Person> personKey = ViewContextKey.of("person");
	private static final ViewContextKey<FileInfoURI> personPictureTmp = ViewContextKey.of("personPictureTmp");

	@Inject
	private PersonServices personServices;

	@GetMapping("/{personId}")
	public void initContext(final ViewContext viewContext, @PathVariable("personId") final Long personId) {
		viewContext.publishDto(personKey, personServices.getPerson(personId));
		toModeReadOnly();
	}

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishDto(personKey, personServices.initPerson());
		toModeCreate();
	}

	@GetMapping("/picture/{protectedUrl}")
	public VFile loadFile(@PathVariable("protectedUrl") final String protectedUrl) {
		final Long fileKey = ProtectedValueUtil.readProtectedValue(protectedUrl, Long.class);
		//project specific part
		return personServices.getPersonPicture(fileKey);
	}

	@PostMapping("/picture")
	public ViewContext uploadFile(final ViewContext viewContext, @Named("file") final VFile personPictureFile) {
		final FileInfoURI personPictureUri = personServices.savePictureTmp(personPictureFile);
		viewContext.publishRef(personPictureTmp, personPictureUri);
		return viewContext;
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_cancel")
	public void doCancel() {
		toModeReadOnly();
	}

	@PostMapping("/_create")
	public String doCreate(
			@ViewAttribute("person") final Person person, final ViewContext viewContext) {
		personServices.createPerson(person);
		if (viewContext.containsKey(personPictureTmp)) {
			final FileInfoURI personPictureUri = (FileInfoURI) viewContext.get(personPictureTmp);
			personServices.savePersonPicture(person.getPersonId(), personPictureUri);
		}
		return "redirect:/hr/person/" + person.getPersonId();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("person") final Person person, final ViewContext viewContext) {
		personServices.updatePerson(person);
		if (viewContext.containsKey(personPictureTmp)) {
			final FileInfoURI personPictureUri = (FileInfoURI) viewContext.get(personPictureTmp);
			personServices.savePersonPicture(person.getPersonId(), personPictureUri);
		}
		return "redirect:/hr/person/" + person.getPersonId();
	}

}
