package io.mars.job.controllers;

import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.job.services.JobServices;
import io.vertigo.orchestra.domain.DtDefinitions.OExecutionSummaryFields;
import io.vertigo.orchestra.domain.DtDefinitions.OProcessUiFields;
import io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.monitoring.domain.uidefinitions.OProcessUi;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/jobs")
public class JobListController extends AbstractVSpringMvcController {

	private static final Integer WEEK_DAYS = 7;
	private static final ViewContextKey<OProcessUi> jobs = ViewContextKey.of("jobs");
	private static final ViewContextKey<OExecutionSummary> summaries = ViewContextKey.of("summaries");

	@Inject
	private JobServices jobServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishDtList(jobs, OProcessUiFields.name, jobServices.getProcessDefinitions());

		// We take the first day of the current week
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		// We make the call with the proper week dates
		viewContext.publishDtList(summaries, OExecutionSummaryFields.proId, jobServices.getSummariesByDate(
				firstDayOfWeek.toInstant(), getFirstDayOfNextWeekDate(firstDayOfWeek), Optional.of("ALL")));

	}

	private static Instant getFirstDayOfNextWeekDate(final Calendar first) {
		// and add seven days to the end date
		final Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, WEEK_DAYS);

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
