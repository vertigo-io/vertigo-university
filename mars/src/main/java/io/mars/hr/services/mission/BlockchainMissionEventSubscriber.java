package io.mars.hr.services.mission;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Business;
import io.mars.hr.domain.Mission;
import io.mars.hr.domain.Person;
import io.mars.hr.services.person.PersonServices;
import io.vertigo.account.account.Account;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.ledger.services.LedgerManager;
import io.vertigo.social.services.notification.Notification;
import io.vertigo.social.services.notification.NotificationServices;

public class BlockchainMissionEventSubscriber implements Component {

	@Inject
	private LedgerManager ledgerManager;

	@Inject
	private PersonServices personServices;
	@Inject
	private NotificationServices notificationServices;

	@EventBusSubscribed
	public void onTicketEvent(final MissionEvent missionEvent) {
		final Mission mission = missionEvent.getMission();
		final Base base = mission.base().get();
		final Person person = mission.person().get();
		final Business business = mission.business().get();
		final StringBuilder sbSerializedTicket = new StringBuilder();
		sbSerializedTicket.append(person.getFullName())
				.append(" has been affected to base ")
				.append(base.getName())
				.append(" with the role ")
				.append(mission.getRole())
				.append(" for the company ")
				.append(business.getName());

		final String message = sbSerializedTicket.toString();
		ledgerManager.sendDataAsync(message, () -> {
			final Notification notification = Notification.builder()
					.withSender("System")
					.withTitle("New assignement sucessfully written to the ledger")
					.withContent(message)
					.withTTLInSeconds(600)
					.withType("MARS-MISSION-LEDGER") //should prefix by app, in case of multi-apps notifications
					.withTargetUrl("#")
					.build();
			sendNotificationToAll(notification);
		});

		final Notification notification = Notification.builder()
				.withSender("System")
				.withTitle("Writing new assignement to the ledger")
				.withContent(sbSerializedTicket.toString())
				.withTTLInSeconds(600)
				.withType("MARS-MISSION-LEDGER") //should prefix by app, in case of multi-apps notifications
				.withTargetUrl("#")
				.build();
		sendNotificationToAll(notification);
	}

	private void sendNotificationToAll(final Notification notification) {
		final Set<UID<Account>> accountUIDs = personServices.getPersons(DtListState.of(null))
				.stream()
				.map((person) -> UID.of(Account.class, String.valueOf(person.getPersonId())))
				.collect(Collectors.toSet());
		notificationServices.send(notification, accountUIDs);
	}

}
