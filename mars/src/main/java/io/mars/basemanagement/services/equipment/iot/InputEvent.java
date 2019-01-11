package io.mars.basemanagement.services.equipment.iot;

import java.util.Optional;

/**
 * This class defines the event that is emitted when an actuator is triggered.
 *
 * @author rfelten
 */
public final class InputEvent extends AbstractIotEvent {

	/**
	 * Constructor.
	 * @param type the type (on/off)
	 */
	public InputEvent(final Type type, final String topic) {
		super(type, topic, Optional.empty());
	}

	public InputEvent(final Type type, final String topic, final String payload) {
		super(type, topic, Optional.of(payload));
	}
}
