package io.mars.basemanagement.services.equipment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.services.equipment.iot.InputEvent;
import io.vertigo.commons.eventbus.EventBusManager;
import io.vertigo.core.component.Component;
import io.vertigo.database.timeseries.ClusteredMeasure;
import io.vertigo.database.timeseries.DataFilter;
import io.vertigo.database.timeseries.TabularDatas;
import io.vertigo.database.timeseries.TimeFilter;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;
import io.vertigo.database.timeseries.TimedDatas;
import io.vertigo.lang.Assertion;

public class EquipmentEnvironmentServices implements Component {
	private static final String APP_NAME = "mars-test"; // TODO: add a param

	@Inject
	private EventBusManager eventBusManager;

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	public TimedDatas getTimeSeries(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter) {
		return timeSeriesDataBaseManager.getTimeSeries(APP_NAME, measures, dataFilter, timeFilter);

	}

	public TimedDatas getClusteredTimeSeries(final ClusteredMeasure clusteredMeasure, final DataFilter dataFilter, final TimeFilter timeFilter) {
		return timeSeriesDataBaseManager.getClusteredTimeSeries(APP_NAME, clusteredMeasure, dataFilter, timeFilter);
	}

	public TimedDatas getTabularTimedData(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter, final String... groupBy) {
		return timeSeriesDataBaseManager.getTabularTimedData(APP_NAME, measures, dataFilter, timeFilter, groupBy);
	}

	public TabularDatas getTabularData(final List<String> measures, final DataFilter dataFilter, final TimeFilter timeFilter, final String... groupBy) {
		return timeSeriesDataBaseManager.getTabularData(APP_NAME, measures, dataFilter, timeFilter, groupBy);
	}

	public TabularDatas getTops(final String measure, final DataFilter dataFilter, final TimeFilter timeFilter, final String groupBy, final int maxRows) {
		return timeSeriesDataBaseManager.getTops(APP_NAME, measure, dataFilter, timeFilter, groupBy, maxRows);
	}

	public List<String> getTagValues(final String measurement, final String tag) {
		return timeSeriesDataBaseManager.getTagValues(APP_NAME, measurement, tag);
	}

	public Double getLastTemperature() {
		final TabularDatas lastTemperatures = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Collections.singletonList("value:last"),
				DataFilter.builder("temperature").build(),
				TimeFilter.builder("now() - 3d", "now() + 3d").build());
		if (!lastTemperatures.getTabularDataSeries().isEmpty()) {
			return Math.round((Double) lastTemperatures.getTabularDataSeries().get(0).getValues().get("value:last") * 10.0) / 10.0;
		}
		return 0.0;
	}

	public Double getLastHumidity() {
		final TabularDatas lastHumidities = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Collections.singletonList("value:last"),
				DataFilter.builder("humidite").build(),
				TimeFilter.builder("now() - 3d", "now() + 3d").build());
		if (!lastHumidities.getTabularDataSeries().isEmpty()) {
			return Math.round((Double) lastHumidities.getTabularDataSeries().get(0).getValues().get("value:last") * 10.0) / 10.0;
		}
		return 0.0;
	}

	public Integer getTotalTemperatureMeasured() {
		//a changer pour V2
		final TabularDatas totalMeasure = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Collections.singletonList("value:count"),
				DataFilter.builder("temperature").build(),
				TimeFilter.builder("now() - 365d", "now()").build());

		if (!totalMeasure.getTabularDataSeries().isEmpty()) {
			return ((Double) totalMeasure.getTabularDataSeries().get(0).getValues().get("value:count")).intValue();
		}
		return 0;
	}

	public void sendBaseAlert() {
		final InputEvent sendAlert = new InputEvent(InputEvent.Type.of(1), "base/fireAlarm");
		eventBusManager.post(sendAlert);
	}

	public void stopBaseAlert() {
		final InputEvent stopAlert = new InputEvent(InputEvent.Type.of(0), "base/fireAlarm");
		eventBusManager.post(stopAlert);
	}

	public void displayMessage(final String message) {
		Assertion.checkArgNotEmpty(message);
		Assertion.checkArgument(message.length() <= 64, "Message size must be <= 64 characters", message);
		//Add escape character
		//send payload to bus to publish message
		final InputEvent sendMessage = new InputEvent(InputEvent.Type.of(1), "base/message", message);
		eventBusManager.post(sendMessage);
	}

	public void turnOnFan() {
		final InputEvent offFan = new InputEvent(InputEvent.Type.of(1), "base/turnFan");
		eventBusManager.post(offFan);
	}

	public void turnOffFan() {
		final InputEvent onFan = new InputEvent(InputEvent.Type.of(0), "base/turnFan");
		eventBusManager.post(onFan);
	}

	public Integer getWeeklyTriggeredAlarm() {
		final TabularDatas totalAlerts = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Collections.singletonList("value:sum"),
				DataFilter.builder("fireAlarm").build(),
				TimeFilter.builder("now() - 15d", "now() + 1h").build());
		if (!totalAlerts.getTabularDataSeries().isEmpty()) {
			return ((Double) totalAlerts.getTabularDataSeries().get(0).getValues().get("value:sum")).intValue();
		}
		return 0;
	}

	public String actionMoistureLevel() {
		final TabularDatas lastMoistureValue = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Arrays.asList("value:last", "equipment:last"),
				DataFilter.builder("moisture").build(),
				TimeFilter.builder("now() - 365d", "now() + 1d").build());
		if (!lastMoistureValue.getTabularDataSeries().isEmpty()) {
			if ((double) Math.round((Double) lastMoistureValue.getTabularDataSeries().get(0).getValues().get("value:last") * 10) / 10 <= 40) {
				return lastMoistureValue.getTabularDataSeries().get(0).getValues().get("equipment:last").toString();
			}
		}
		return "No Farms to Water";
	}
}
