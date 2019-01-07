package io.mars.basemanagement.services.equipment.iot;

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
	public OutputEvent(final Type type, final String topic) {
		super(type, topic);
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
