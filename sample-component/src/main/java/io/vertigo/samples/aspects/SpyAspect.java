package io.vertigo.samples.aspects;

import io.vertigo.core.component.aop.Aspect;
import io.vertigo.core.component.aop.AspectMethodInvocation;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * Aspect pour la gestion des transactions au niveau
 * de la couche service.
 * @author prahmoune
 */
public final class SpyAspect implements Aspect {
	private final SpyManager spyManager;

	@Inject
	public SpyAspect(final SpyManager spyManager) {
		Assertion.checkNotNull(spyManager);
		//-----
		this.spyManager = spyManager;
	}

	@Override
	public Object invoke(final Object[] args, final AspectMethodInvocation methodInvocation) throws Exception {
		spyManager.log("before " + methodInvocation.getMethod().getName());
		final Object res = methodInvocation.proceed(args);
		spyManager.log("after " + methodInvocation.getMethod().getName());
		return res;
	}

	@Override
	public Class<?> getAnnotationType() {
		return Spy.class;
	}
}
