package io.mars.basemanagement.controllers.base;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseOverview;
import io.mars.basemanagement.domain.Geosector;
import io.mars.basemanagement.domain.Picture;
import io.mars.basemanagement.domain.Tag;
import io.mars.basemanagement.services.base.BaseServices;
import io.mars.domain.DtDefinitions.PictureFields;
import io.mars.hr.domain.Person;
import io.mars.hr.services.mission.MissionServices;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.lang.VUserException;
import io.vertigo.ui.core.BasicUiListModifiable;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.vega.webservice.stereotype.QueryParam;
import io.vertigo.vega.webservice.validation.UiMessageStack;

@Controller
@RequestMapping("/basemanagement/base/information")
public class BaseInformationController extends AbstractVSpringMvcController {

	@Inject
	private BaseServices baseServices;
	@Inject
	private MissionServices missionServices;
	@Inject
	private BaseDetailController baseDetailController;

	private static final ViewContextKey<Person> baseManagerKey = ViewContextKey.of("baseManager");
	private static final ViewContextKey<Geosector> geosectorsKey = ViewContextKey.of("geosectors");
	private static final ViewContextKey<Tag> tagsKey = ViewContextKey.of("tags");
	private static final ViewContextKey<BaseOverview> baseOverview = ViewContextKey.of("baseOverview");
	private static final ViewContextKey<Picture> basePictures = ViewContextKey.of("basePictures");

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		baseDetailController.initCommonContext(viewContext, baseId);
		viewContext.publishMdl(tagsKey, Tag.class, null); //all
		viewContext.publishDtList(geosectorsKey, baseServices.getAllGeosectors());
		//---
		viewContext.publishDto(baseOverview, baseServices.getBaseOverview(baseId));
		//---
		final Person noManagerPerson = new Person();
		noManagerPerson.setLastName("No manager");
		//---
		viewContext.publishDto(baseManagerKey, missionServices.getBaseManager(baseId).orElse(noManagerPerson));

		viewContext.publishDtListModifiable(basePictures, baseServices.getPictures(baseId));

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
	public String doSave(final ViewContext viewContext, @ViewAttribute("base") final Base base, @QueryParam("baseTmpPictureUris") final List<FileInfoURI> addedPictureFile, final UiMessageStack uiMessageStack) {
		final BasicUiListModifiable<Picture> pictures = viewContext.getUiListModifiable(basePictures);
		pictures.mergeAndCheckInput(Collections.EMPTY_LIST, uiMessageStack); //needed to populate Delta
		baseServices.save(base, addedPictureFile, pictures.getDtListDelta().getDeleted()); //Warning : always one service call : one transaction
		return "redirect:/basemanagement/base/information/" + base.getBaseId();
	}

	@PostMapping("/_remove")
	public ViewContext doRemove(final ViewContext viewContext, @RequestParam("basePictureId") final String protectedId) {
		//baseServices.removeBasePicture(baseId, basePictureId);
		final BasicUiListModifiable<Picture> pictures = viewContext.getUiListModifiable(basePictures);
		final Long basePictureId = ProtectedValueUtil.readProtectedValue(protectedId, Long.class);
		final boolean elementRemoved = pictures.removeIf((picture) -> basePictureId.equals(picture.getLong(PictureFields.picturefileId.name())));
		if (!elementRemoved) {
			throw new VUserException("Picture already removed");
		}
		viewContext.markModifiedKeys(basePictures);
		return viewContext;
		//return "redirect:/basemanagement/base/information/" + baseId;
	}

	@PostMapping("/_ajaxValidation")
	public ViewContext doAjaxValidation(final ViewContext viewContext, @ViewAttribute("base") final Base base) {
		// do something or just validation from retrieving a view attribute
		return viewContext;
	}

}
