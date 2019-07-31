package io.mars.opendata.controllers;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Tag;
import io.mars.opendata.domain.OpendataSet;
import io.mars.opendata.services.OpendataSetServices;
import io.vertigo.core.param.ParamValue;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/opendata/opendataset")
public class OpendataSetDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<OpendataSet> opendataSetKey = ViewContextKey.of("opendataSet");
	private static final ViewContextKey<Tag> tagsKey = ViewContextKey.of("tags");

	@Inject
	private OpendataSetServices opendataSetServices;

	@GetMapping("/{odsId}")
	public void initContext(final ViewContext viewContext, @PathVariable("odsId") final Long odsId) {
		viewContext.publishMdl(tagsKey, Tag.class, null); //all
		viewContext.publishDto(opendataSetKey, opendataSetServices.getOpendataSet(odsId));
		toModeReadOnly();
	}

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		//viewContext.publishDto(opendataSetKey, opendataSetServices.init());
		toModeCreate();
	}

	@GetMapping("/picture/{protectedUrl}")
	public VFile loadFile(@PathVariable("protectedUrl") final String protectedUrl) {
		//project specific part
		final Long fileKey = ProtectedValueUtil.readProtectedValue(protectedUrl, Long.class);
		return opendataSetServices.getOpendataSetPicture(fileKey);
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
	public String doCreate(@ViewAttribute("opendataSet") final OpendataSet opendataSet, @ParamValue("opendataSetTmpPictureUri") final Optional<FileInfoURI> opendataSetPictureFile) {
		opendataSetServices.createOpendataSet(opendataSet);
		if (opendataSetPictureFile.isPresent()) {
			opendataSetServices.saveOpendataSetPicture(opendataSet.getOdsId(), opendataSetPictureFile.get());
		}
		return "redirect:/opendata/opendataset/" + opendataSet.getOdsId();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("opendataSet") final OpendataSet opendataSet, @ParamValue("opendataSetTmpPictureUri") final Optional<FileInfoURI> opendataSetPictureFile) {
		opendataSetServices.updateOpendataSet(opendataSet);
		if (opendataSetPictureFile.isPresent()) {
			opendataSetServices.saveOpendataSetPicture(opendataSet.getOdsId(), opendataSetPictureFile.get());
		}
		return "redirect:/opendata/opendataset/" + opendataSet.getOdsId();
	}

}
