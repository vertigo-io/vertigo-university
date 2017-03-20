package io.vertigo.notifications.aspects.trace;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import io.vertigo.core.component.aop.Aspect;
import io.vertigo.core.component.aop.AspectMethodInvocation;

/**
 * @author dt
 */
public final class TraceAspect implements Aspect {
	private final TraceManager traceManager;

	@Inject
	public TraceAspect(final TraceManager traceManager) {
		this.traceManager = traceManager;
	}

	@Override
	public Object invoke(final Object[] args, final AspectMethodInvocation methodInvocation) {
		traceManager.traceBefore(methodInvocation.getMethod().getName(), args);
		final Object ret = methodInvocation.proceed(args);
		traceManager.traceAfter(methodInvocation.getMethod().getName(), ret);
		return ret;
	}

	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return Trace.class;
	}
}
