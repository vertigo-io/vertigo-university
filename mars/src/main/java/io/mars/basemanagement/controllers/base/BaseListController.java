package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.services.base.BaseServices;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/bases")
public class BaseListController extends AbstractVSpringMvcController {

	private static final ViewContextKey<Base> bases = ViewContextKey.of("bases");

	@Inject
	private BaseServices baseServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		final DtListState dtListState = new DtListState(200, 0, null, null);
		viewContext.publishDtList(bases, baseServices.getBases(dtListState));
	}

	@PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
		viewContext.publishDtList(bases, baseServices.getBases(dtListState));
		return viewContext;
	}

}
