package io.mars.basemanagement.services.equipment.iot;

import io.vertigo.commons.eventbus.Event;
import io.vertigo.database.timeseries.Measure;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when an measure is sent.
 *
 * @author rfelten
 */
public final class MeasureEvent implements Event {
	private final Measure measure;

	/**
	 * Constructor.
	 * @param measure the measure
	 */
	public MeasureEvent(final Measure measure) {
		Assertion.checkNotNull(measure);
		//-----
		this.measure = measure;
	}

	public Measure getMeasure() {
		return measure;
	}
}
