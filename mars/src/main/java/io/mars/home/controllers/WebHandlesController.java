package io.mars.home.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.VSystemException;
import io.vertigo.social.services.handle.HandleServices;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/hw")
public class WebHandlesController extends AbstractVSpringMvcController {

	@Inject
	private HandleServices handleServices;

	@GetMapping("/{definitionName}/{code}")
	public String forward(@PathVariable("definitionName") final String definitionName, @PathVariable("code") final String code) {
		Assertion.checkArgNotEmpty(definitionName);
		Assertion.checkArgNotEmpty(code);
		//---
		final String handle = definitionName + "/" + code;
		final UID uid = handleServices.getHandleByCode(handle).getUid();
		switch (uid.getDefinition().getName()) {
			case "DtBase":
				return "redirect:/basemanagement/base/information/" + uid.getId();
			case "DtEquipment":
				return "redirect:/basemanagement/equipment/" + uid.getId();
			default:
				throw new VSystemException("handle {0} is not linkable to a web resource", handle);
		}

	}

}
