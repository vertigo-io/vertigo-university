package io.vertigo.notifications.aspects.trace;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

/**
 *
 * @author dt
 *
 */
public class TraceManagerImpl implements TraceManager {

	private static final Logger LOG = Logger.getLogger(TraceManagerImpl.class);

	@Override
	public void traceBefore(final String name, final Object[] args) {

		final String values = Stream.of(args)
				.map(String::valueOf)
				.collect(Collectors.joining(",", "(", ")"));

		LOG.info("Before calling " + name + " parameters " + values);
	}

	@Override
	public void traceAfter(final String name, final Object ret) {
		LOG.info("After calling " + name + " return value : " + String.valueOf(ret));
	}

}
