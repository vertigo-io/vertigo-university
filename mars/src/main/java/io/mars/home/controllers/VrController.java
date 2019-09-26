package io.mars.home.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.services.base.BaseServices;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/vr")
public class VrController extends AbstractVSpringMvcController {

	@Inject
	private BaseServices baseServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		// nothing
		final DtList<Base> bases = baseServices.getBases(DtListState.of(20));
		viewContext.publishDtList(() -> "bases", bases);
	}

}
