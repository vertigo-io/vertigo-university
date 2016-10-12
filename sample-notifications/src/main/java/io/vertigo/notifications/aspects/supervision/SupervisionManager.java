package io.vertigo.notifications.aspects.supervision;

import io.vertigo.lang.Manager;

/**
 *
 * @author dt
 *
 */
public interface SupervisionManager extends Manager {

	void superviseExec(final String methodName, long start, long stop);
}
