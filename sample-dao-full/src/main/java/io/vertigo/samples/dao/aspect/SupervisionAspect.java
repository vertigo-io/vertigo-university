package io.vertigo.samples.dao.aspect;

import java.lang.annotation.Annotation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.core.node.component.aspect.Aspect;
import io.vertigo.core.node.component.aspect.AspectMethodInvocation;

/**
 *
 * @author dt
 *
 */
public class SupervisionAspect implements Aspect {

	private static final Logger LOG = LogManager.getLogger(SupervisionAspect.class);

	@Override
	public Object invoke(final Object[] args, final AspectMethodInvocation methodInvocation) {
		final long start = System.currentTimeMillis();
		try {
			// We call the annotated method
			return methodInvocation.proceed(args);
		} finally {
			final long stop = System.currentTimeMillis();
			LOG.info("Method: " + methodInvocation.getMethod().getName() + "Execuction time (ms) : " + (stop - start));
		}
	}

	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return Supervision.class;
	}

}
