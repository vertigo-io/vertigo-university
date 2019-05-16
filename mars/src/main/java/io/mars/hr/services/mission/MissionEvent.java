package io.mars.hr.services.mission;

import io.mars.hr.domain.Mission;
import io.vertigo.commons.eventbus.Event;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when a mission is created
 *
 * @author mlaroche
 */
public final class MissionEvent implements Event {

	private final Mission mission;

	/**
	 * Constructor.
	 * @param type Store type
	 * @param ticket The ticket
	 */
	public MissionEvent(final Mission mission) {
		Assertion.checkNotNull(mission);
		//-----
		this.mission = mission;
	}

	/**
	 * @return Mission The mission
	 */
	public Mission getMission() {
		return mission;
	}

}
