package io.vertigo.samples.vui.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.vertigo.samples.vui.domain.Actor;
import io.vertigo.samples.vui.domain.Role;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/roleActor")
public class RoleActorController extends AbstractVSpringMvcController {

	@Inject
	private MovieServices movieServices;

	private static final ViewContextKey<Role> roleKey = ViewContextKey.of("role");
	private static final ViewContextKey<Actor> actorKey = ViewContextKey.of("actor");
	private static final ViewContextKey<String> successCallbackKey = ViewContextKey.of("successCallback");
	private static final ViewContextKey<Boolean> closeSuccessKey = ViewContextKey.of("closeSuccess");

	@GetMapping("/{roleId}")
	public void initContext(final ViewContext viewContext, @PathVariable("roleId") final Long roleId, @RequestParam("successCallback") final String successCallback) {
		final Role role = movieServices.getRoleWithActorById(roleId);
		viewContext.publishDto(roleKey, role);
		viewContext.publishDto(actorKey, role.actor().get());
		viewContext.publishRef(successCallbackKey, successCallback);
		viewContext.publishRef(closeSuccessKey, Boolean.FALSE);
		//---
		toModeEdit();
	}

	@PostMapping("/_save")
	public void doSave(final ViewContext viewContext, @ViewAttribute("role") final Role role, @ViewAttribute("actor") final Actor actor) {
		movieServices.save(role, actor);
		viewContext.publishRef(closeSuccessKey, Boolean.TRUE);
	}

}
