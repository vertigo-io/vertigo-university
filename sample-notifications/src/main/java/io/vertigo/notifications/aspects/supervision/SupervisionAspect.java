package io.vertigo.notifications.aspects.supervision;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import io.vertigo.core.component.aop.Aspect;
import io.vertigo.core.component.aop.AspectMethodInvocation;

/**
 *
 * @author dt
 *
 */
public class SupervisionAspect implements Aspect {
	private final SupervisionManager supervisionManager;

	@Inject
	public SupervisionAspect(final SupervisionManager supervisionManager) {
		this.supervisionManager = supervisionManager;
	}

	@Override
	public Object invoke(final Object[] args, final AspectMethodInvocation methodInvocation) {
		final long start = System.currentTimeMillis();
		try {
			// We call the annotated method
			return methodInvocation.proceed(args);
		} finally {
			final long stop = System.currentTimeMillis();
			supervisionManager.superviseExec(methodInvocation.getMethod().getName(), start, stop);
		}
	}

	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return Supervision.class;
	}

}
