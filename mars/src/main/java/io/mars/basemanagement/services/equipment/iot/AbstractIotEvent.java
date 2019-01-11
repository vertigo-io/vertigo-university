package io.mars.basemanagement.services.equipment.iot;

import java.util.Optional;

import org.bouncycastle.crypto.RuntimeCryptoException;

import io.vertigo.commons.eventbus.Event;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when an actuator is triggered.
 *
 * @author rfelten
 */
public class AbstractIotEvent implements Event {
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
	private final String topic;
	private final Optional<String> payloadOpt;

	/**
	 * Constructor.
	 * @param type the type (on/off)
	 */
	protected AbstractIotEvent(final Type type, final String topic, final Optional<String> payloadOpt) {
		Assertion.checkNotNull(type);
		Assertion.checkArgNotEmpty(topic);
		Assertion.checkNotNull(payloadOpt);
		//-----
		this.type = type;
		this.topic = topic;
		this.payloadOpt = payloadOpt;
	}

	/**
	 * @return Store type
	 */
	public final Type getType() {
		return type;
	}

	public final String getTopic() {
		return topic;
	}

	public Optional<String> getPayloadOpt() {
		return payloadOpt;
	}
}
