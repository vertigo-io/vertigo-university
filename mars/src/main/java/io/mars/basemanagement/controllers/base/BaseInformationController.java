package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseType;
import io.mars.basemanagement.domain.Geosector;
import io.mars.basemanagement.domain.Tag;
import io.mars.basemanagement.services.base.BaseServices;
import io.mars.hr.domain.Person;
import io.mars.hr.services.mission.MissionServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/base/information")
public class BaseInformationController extends AbstractVSpringMvcController {

	@Inject
	private BaseServices baseServices;
	@Inject
	private MissionServices missionServices;

	private final ViewContextKey<Base> baseKey = ViewContextKey.of("base");
	private final ViewContextKey<Person> baseManagerKey = ViewContextKey.of("baseManager");
	private final ViewContextKey<BaseType> baseTypesKey = ViewContextKey.of("baseTypes");
	private final ViewContextKey<Geosector> geosectorsKey = ViewContextKey.of("geosectors");
	private final ViewContextKey<Tag> tagsKey = ViewContextKey.of("tags");

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		viewContext.publishMdl(baseTypesKey, BaseType.class, null); //all
		viewContext.publishMdl(tagsKey, Tag.class, null); //all
		viewContext.publishDtList(geosectorsKey, baseServices.getAllGeosectors());
		viewContext.publishDto(baseKey, baseServices.get(baseId));
		//---
		final Person noManagerPerson = new Person();
		noManagerPerson.setLastName("No manager");
		//---
		viewContext.publishDto(baseManagerKey, missionServices.getBaseManager(baseId).orElse(noManagerPerson));
		toModeReadOnly();
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_cancel")
	public void doCancel() {
		toModeReadOnly();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("base") final Base base) {
		baseServices.save(base);
		return "redirect:/basemanagement/base/information/" + base.getBaseId();
	}

}
