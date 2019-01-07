package io.mars.basemanagement.services.equipment.iot;

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
		super(type, topic);
	}
}
