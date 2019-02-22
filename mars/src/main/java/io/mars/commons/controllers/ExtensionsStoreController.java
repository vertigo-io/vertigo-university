package io.mars.commons.controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.commons.node.NodeManager;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/extensionsStore")
public class ExtensionsStoreController extends AbstractVSpringMvcController {

	@Inject
	private NodeManager nodeManager;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishRef(() -> "skills", (ArrayList<String>) nodeManager.getCurrentNode().getSkills());
	}

}
