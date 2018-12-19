package io.mars.basemanagement.services.equipment;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.core.component.Component;
import io.vertigo.database.timeseries.ClusteredMeasure;
import io.vertigo.database.timeseries.DataFilter;
import io.vertigo.database.timeseries.TabularDatas;
import io.vertigo.database.timeseries.TimeFilter;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;
import io.vertigo.database.timeseries.TimedDatas;
import io.vertigo.lang.Assertion;

public class EquipmentEnvironmentServices implements Component {
	private final String appName = "mars-test"; // TODO: add a param

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	public TimedDatas getTimeSeries(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter) {
		Assertion.checkNotNull(measures);
		Assertion.checkNotNull(dataFilter);
		Assertion.checkNotNull(timeFilter.getDim());// we check dim is not null because we need it
		//---
		return timeSeriesDataBaseManager.getTimeSeries(appName, measures, dataFilter, timeFilter);

	}

	public TimedDatas getClusteredTimeSeries(final ClusteredMeasure clusteredMeasure, final DataFilter dataFilter, final TimeFilter timeFilter) {
		Assertion.checkNotNull(dataFilter);
		Assertion.checkNotNull(timeFilter);
		Assertion.checkNotNull(timeFilter.getDim()); // we check dim is not null because we need it
		Assertion.checkNotNull(clusteredMeasure);
		//---
		Assertion.checkArgNotEmpty(clusteredMeasure.getMeasure());
		Assertion.checkNotNull(clusteredMeasure.getThresholds());
		Assertion.checkState(!clusteredMeasure.getThresholds().isEmpty(), "For clustering the measure '{0}' you need to provide at least one threshold", clusteredMeasure.getMeasure());
		//we use the natural order
		clusteredMeasure.getThresholds().sort(Comparator.naturalOrder());
		//---
		return timeSeriesDataBaseManager.getClusteredTimeSeries(appName, clusteredMeasure, dataFilter, timeFilter);
	}

	public TimedDatas getTabularTimedData(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter, final String... groupBy) {
		return timeSeriesDataBaseManager.getTabularTimedData(appName, measures, dataFilter, timeFilter, groupBy);
	}

	public TabularDatas getTabularData(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter, final String... groupBy) {
		return timeSeriesDataBaseManager.getTabularData(appName, measures, dataFilter, timeFilter, groupBy);
	}

	public TabularDatas getTops(final String measure, final DataFilter dataFilter, final TimeFilter timeFilter, final String groupBy, final int maxRows) {
		return timeSeriesDataBaseManager.getTops(appName, measure, dataFilter, timeFilter, groupBy, maxRows);
	}

	public List<String> getTagValues(final String measurement, final String tag) {
		return timeSeriesDataBaseManager.getTagValues(appName, measurement, tag);
	}
}
