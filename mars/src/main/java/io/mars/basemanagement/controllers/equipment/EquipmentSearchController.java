package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.search.EquipmentIndex;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.mars.domain.DtDefinitions.EquipmentIndexFields;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipments")
public class EquipmentSearchController extends AbstractVSpringMvcController {

	private static final ViewContextKey<String> criteriaKey = ViewContextKey.of("criteria");
	private static final ViewContextKey<FacetedQueryResult<EquipmentIndex, SearchQuery>> equipments = ViewContextKey.of("equipments");

	@Inject
	private EquipmentServices equipmentServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishRef(criteriaKey, "");
		final FacetedQueryResult<EquipmentIndex, SearchQuery> facetedQueryResult = equipmentServices.searchEquipments("", SelectedFacetValues.empty().build(), DtListState.defaultOf(Equipment.class));
		viewContext.publishFacetedQueryResult(equipments, EquipmentIndexFields.equipmentId, facetedQueryResult, criteriaKey);
	}

	@PostMapping("/_search")
	public ViewContext doSearch(
			final ViewContext viewContext,
			@ViewAttribute("criteria") final String criteria,
			@ViewAttribute("equipments") final SelectedFacetValues selectedFacetValues,
			final DtListState dtListState) {
		final FacetedQueryResult<EquipmentIndex, SearchQuery> facetedQueryResult = equipmentServices.searchEquipments(criteria, selectedFacetValues, dtListState);
		viewContext.publishFacetedQueryResult(equipments, EquipmentIndexFields.equipmentId, facetedQueryResult, criteriaKey);
		return viewContext;
	}

}
