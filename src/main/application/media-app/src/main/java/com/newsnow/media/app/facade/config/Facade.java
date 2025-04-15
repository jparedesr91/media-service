package com.newsnow.media.app.facade.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "Business Service Facade". This annotation serves as a specialization of
 * {@link Service @Service}, allowing for implementation classes to be autodetected through classpath scanning the
 * spring framework does.
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Facade {
    String value() default "";
}
