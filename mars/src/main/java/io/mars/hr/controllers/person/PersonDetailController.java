package io.mars.hr.controllers.person;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Tag;
import io.mars.domain.DtDefinitions.MissionDisplayFields;
import io.mars.hr.domain.MissionDisplay;
import io.mars.hr.domain.Person;
import io.mars.hr.services.mission.MissionServices;
import io.mars.hr.services.person.PersonServices;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@Controller
@RequestMapping("/hr/person")
public class PersonDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Person> personKey = ViewContextKey.of("person");
	private static final ViewContextKey<MissionDisplay> missionsKey = ViewContextKey.of("missions");
	private static final ViewContextKey<Tag> tagsKey = ViewContextKey.of("tags");

	@Inject
	private PersonServices personServices;
	@Inject
	private MissionServices missionServices;

	@GetMapping("/{personId}")
	public void initContext(final ViewContext viewContext, @PathVariable("personId") final Long personId) {
		viewContext.publishMdl(tagsKey, Tag.class, null); //all
		viewContext.publishDto(personKey, personServices.getPerson(personId));
		viewContext.publishDtList(missionsKey, MissionDisplayFields.missionId, missionServices.getMissionsByPersonId(personId));
		toModeReadOnly();
	}

	@GetMapping("/new")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishDto(personKey, personServices.initPerson());
		viewContext.publishMdl(tagsKey, Tag.class, null); //all
		toModeCreate();
	}

	@GetMapping("/picture/{protectedUrl}")
	public VFile loadFile(@PathVariable("protectedUrl") final String protectedUrl) {
		//project specific part
		final Long fileKey = ProtectedValueUtil.readProtectedValue(protectedUrl, Long.class);
		return personServices.getPersonPicture(fileKey);
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
	public String doCreate(@ViewAttribute("person") final Person person, @QueryParam("personTmpPictureUri") final Optional<FileInfoURI> personPictureFile) {
		personServices.createPerson(person);
		if (personPictureFile.isPresent()) {
			personServices.savePersonPicture(person.getPersonId(), personPictureFile.get());
		}
		return "redirect:/hr/person/" + person.getPersonId();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("person") final Person person, @QueryParam("personTmpPictureUri") final Optional<FileInfoURI> personPictureFile) {
		personServices.updatePerson(person);
		if (personPictureFile.isPresent()) {
			personServices.savePersonPicture(person.getPersonId(), personPictureFile.get());
		}
		return "redirect:/hr/person/" + person.getPersonId();
	}

	@PostMapping("/_reloadMissions")
	public ViewContext doReloadMissions(final ViewContext viewContext, @ViewAttribute("person") final Person person) {
		viewContext.publishDtList(missionsKey, MissionDisplayFields.missionId, missionServices.getMissionsByPersonId(person.getPersonId()));
		return viewContext;
	}

}
