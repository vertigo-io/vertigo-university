package io.vertigo.notifications.aspects.trace;

import io.vertigo.core.node.component.Manager;

/**
 *
 * @author dt
 *
 */
public interface TraceManager extends Manager {

	/**
	 *
	 * @param name
	 * @param parameters
	 */
	void traceBefore(final String name, final Object[] args);

	/**
	 *
	 * @param name
	 * @param parameters
	 */
	void traceAfter(final String name, final Object ret);

}
