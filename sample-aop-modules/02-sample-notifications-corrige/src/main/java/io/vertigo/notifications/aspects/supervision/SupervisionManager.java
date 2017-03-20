package io.vertigo.notifications.aspects.supervision;

import io.vertigo.lang.Manager;

/**
 * @author dt
 */
public interface SupervisionManager extends Manager {

	/**
	 *
	 * @param methodName
	 * @param start
	 * @param stop
	 */
	void superviseExec(final String methodName, long start, long stop);
}
