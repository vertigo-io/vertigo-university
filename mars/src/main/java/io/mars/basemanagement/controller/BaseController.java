package io.mars.basemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.services.base.BaseServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/base")
public class BaseController extends AbstractVSpringMvcController {

	private final ViewContextKey<Base> baseKey = ViewContextKey.of("base");

	@Autowired
	private BaseServices baseServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext, @RequestParam("basId") final Long basId) {
		final Base base = baseServices.get(basId);
		viewContext.publishDto(baseKey, base);
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_save")
	public String doSave(
			@ViewAttribute("base") final Base base, final RedirectAttributes redirectAttributes) {
		baseServices.save(base);
		redirectAttributes.addAttribute("basId", base.getBaseId());
		return "redirect:/base/";
	}

}
