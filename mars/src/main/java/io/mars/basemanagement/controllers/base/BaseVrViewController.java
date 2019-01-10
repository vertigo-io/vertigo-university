package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/base/vr")
public class BaseVrViewController extends AbstractVSpringMvcController {

	@Inject
	private BaseDetailController baseDetailController;

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		baseDetailController.initCommonContext(viewContext, baseId);
		toModeReadOnly();
	}

}
