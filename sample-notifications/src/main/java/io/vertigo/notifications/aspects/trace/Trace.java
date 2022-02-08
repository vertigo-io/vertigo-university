package io.vertigo.notifications.aspects.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.vertigo.core.node.component.aspect.AspectAnnotation;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@AspectAnnotation
public @interface Trace {
	//
}
