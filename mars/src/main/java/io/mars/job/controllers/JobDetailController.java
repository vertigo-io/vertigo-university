package io.mars.job.controllers;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.job.services.JobServices;
import io.vertigo.core.param.ParamValue;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.plugins.services.MapCodec;
import io.vertigo.orchestra.services.OrchestraServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/jobDetail")
public class JobDetailController extends AbstractVSpringMvcController {

	private static final ViewContextKey<OProcessUi> jobKey = ViewContextKey.of("job");
	private static final ViewContextKey<OExecutionSummary> executionSummary = ViewContextKey.of("executionSummary");

	@Inject
	private JobServices jobServices;
	@Inject
	private OrchestraDefinitionManager orchestraDefinitionManager;
	@Inject
	private OrchestraServices orchestraServices;

	private static final MapCodec mapCodec = new MapCodec();

	@GetMapping("/{jobName}")
	public void initContext(final ViewContext viewContext, @PathVariable("jobName") final String jobName) {
		viewContext.publishDto(jobKey, jobServices.getProcessDefinition(jobName));
		// We take the first day of the current week
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		viewContext.publishDto(executionSummary, jobServices.getSummaryByDate(jobName, firstDayOfWeek.toInstant(), getFirstDayOfNextWeekDate(firstDayOfWeek)));
		toModeReadOnly();
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_cancel")
	public String doCancel(@ViewAttribute("job") final OProcessUi oProcessUi) {
		return "redirect:/jobDetail/" + oProcessUi.getName();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("job") final OProcessUi job, @ParamValue("personTmpPictureUri") final Optional<FileInfoURI> personPictureFile) {
		orchestraDefinitionManager.updateProcessDefinitionProperties(
				job.getName(),
				Optional.ofNullable(job.getCronExpression()),
				job.getMultiexecution(),
				job.getRescuePeriod(),
				job.getActive());
		orchestraDefinitionManager.updateProcessDefinitionInitialParams(job.getName(), mapCodec.decode(job.getInitialParams()));
		return "redirect:/jobDetail/" + job.getName();
	}

	@PostMapping("/_executeNow")
	public ViewContext doExecuteNow(final ViewContext viewContext, @ViewAttribute("job") final OProcessUi job) {
		orchestraServices.getScheduler().scheduleAt(orchestraDefinitionManager.getProcessDefinition(job.getName()), Instant.now(), Collections.emptyMap());
		return viewContext;
	}

	private static Instant getFirstDayOfNextWeekDate(final Calendar first) {
		// and add seven days to the end date
		final Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 7);

		return last.toInstant();
	}

	private static Calendar getFirstDayOfWeek() {
		final Calendar cal = new GregorianCalendar(Locale.FRANCE);
		// "calculate" the start date of the week
		final Calendar first = (Calendar) cal.clone();
		first.set(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek());
		first.set(Calendar.HOUR_OF_DAY, 0);
		first.set(Calendar.MINUTE, 0);
		first.set(Calendar.SECOND, 0);

		first.set(Calendar.MILLISECOND, 0);

		return first;

	}

}
