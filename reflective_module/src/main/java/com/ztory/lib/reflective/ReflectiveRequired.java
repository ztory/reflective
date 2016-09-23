package com.ztory.lib.reflective;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to flag method as Required, used by Reflective to validate data in an instance.
 * Created by jonruna on 20/04/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReflectiveRequired {
    double minimumNumber() default 0.0d;
    boolean hasMinimumNumber() default false;
}
