package io.vertigo.samples.aspects;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.component.aspect.Aspect;
import io.vertigo.core.node.component.aspect.AspectMethodInvocation;

/**
 * @author pchretien
 */
public final class SpyAspect implements Aspect {
	private final SpyManager spyManager;

	@Inject
	public SpyAspect(final SpyManager spyManager) {
		Assertion.check().isNotNull(spyManager);
		//-----
		this.spyManager = spyManager;
	}

	@Override
	public Object invoke(final Object[] args, final AspectMethodInvocation methodInvocation) {
		spyManager.log("before " + methodInvocation.getMethod().getName());
		final Object res = methodInvocation.proceed(args);
		spyManager.log("after " + methodInvocation.getMethod().getName());
		return res;
	}

	@Override
	public Class<Spy> getAnnotationType() {
		return Spy.class;
	}
}
