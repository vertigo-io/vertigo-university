package io.mars.maintenance.services.workorder;

import io.mars.maintenance.domain.WorkOrder;
import io.vertigo.commons.eventbus.Event;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when the store deals with an object identified by an uri.
 *
 * @author pchretien
 */
public final class WorkOrderEvent implements Event {
	/**
	 * Type of event.
	 */
	public enum Type {
		/** Creation. */
		CREATE,
		/** Closing. */
		CLOSE
	}

	private final Type type;
	private final WorkOrder workOrder;

	/**
	 * Constructor.
	 * @param type Store type
	 * @param workOrder The work order
	 */
	public WorkOrderEvent(final Type type, final WorkOrder workOrder) {
		Assertion.checkNotNull(type);
		Assertion.checkNotNull(workOrder);
		//-----
		this.type = type;
		this.workOrder = workOrder;
	}

	/**
	 * @return WorkOrder The work order
	 */
	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	/**
	 * @return Store type
	 */
	public Type getType() {
		return type;
	}
}
