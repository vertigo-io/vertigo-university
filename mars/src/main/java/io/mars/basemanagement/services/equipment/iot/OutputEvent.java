package io.mars.basemanagement.services.equipment.iot;

import java.util.Optional;

/**
 * This class defines the event that is emitted when an actuator is triggered.
 *
 * @author rfelten
 */
public final class OutputEvent extends AbstractIotEvent {

	/**
	 * Constructor.
	 * @param type the type (on/off)
	 */
	public OutputEvent(final Type type, final String topic, final String payload) {
		super(type, topic, Optional.of(payload));
	}

	public OutputEvent(final Type type, final String topic) {
		super(type, topic, Optional.empty());
	}

	public String getValue() {
		switch (getType()) {
			case ON:
				return "1";
			case OFF:
				return "0";
			default:
				throw new IllegalStateException();
		}
	}
}
