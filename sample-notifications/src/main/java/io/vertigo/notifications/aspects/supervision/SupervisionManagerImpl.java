package io.vertigo.notifications.aspects.supervision;

import org.apache.log4j.Logger;

/**
 *
 * @author dt
 *
 */
public class SupervisionManagerImpl implements SupervisionManager {
	private static final Logger LOG = Logger.getLogger(SupervisionManagerImpl.class);

	@Override
	public void superviseExec(final String methodName, final long start, final long stop) {
		final long ellapsedMillis = (stop - start);

		LOG.info("Method: " + methodName + "Execuction time (ms) : " + ellapsedMillis);
	}

}
