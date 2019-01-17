package io.mars.hr.services.mission;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Business;
import io.mars.hr.domain.Mission;
import io.mars.hr.domain.Person;
import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.ledger.services.LedgerManager;

public class BlockchainMissionEventSubscriber implements Component {

	private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

	@Inject
	private LedgerManager ledgerManager;

	@EventBusSubscribed
	public void onTicketEvent(final MissionEvent missionEvent) {
		final Mission mission = missionEvent.getMission();
		final Base base = mission.base().get();
		final Person person = mission.person().get();
		final Business business = mission.business().get();
		final StringBuilder sbSerializedTicket = new StringBuilder();
		sbSerializedTicket.append(person.getFullName())
		.append(" a été affecté à la base ")
		.append(base.getName())
		.append(" pour le role de ")
		.append(mission.getRole())
		.append(" pour l'entreprise ")
		.append(business.getName());

		messageQueue.add(sbSerializedTicket.toString());
	}

	/**
	 * Daemon to unstack processes to end them
	 */
	@DaemonScheduled(name = "DMN_FLUSH_LEDGER_MESSAGES_MISSION", periodInSeconds = 10)
	public void pollQueue() {
		while (!messageQueue.isEmpty()) {
			final String message = messageQueue.poll();
			if (message != null) {
				ledgerManager.sendData(message);
			}
		}
	}
}
