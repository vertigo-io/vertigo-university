package io.mars.basemanagement.services.equipment;

import org.bouncycastle.crypto.RuntimeCryptoException;

import io.vertigo.commons.eventbus.Event;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when an actuator is triggered.
 *
 * @author rfelten
 */
public final class ActuatorEvent implements Event {
	/**
	 * Type of event.
	 */
	public enum Type {
		/** Creation. */
		ON,
		/** Closing. */
		OFF;

		public static Type of(final Integer value) {
			switch (value) {
				case 0:
					return OFF;
				case 1:
					return ON;
				default:
					throw new RuntimeCryptoException("Unknown value : " + value);
			}
		}
	}

	private final Type type;

	/**
	 * Constructor.
	 * @param type the type (on/off)
	 */
	public ActuatorEvent(final Type type) {
		Assertion.checkNotNull(type);
		//-----
		this.type = type;
	}

	/**
	 * @return Store type
	 */
	public Type getType() {
		return type;
	}
}
