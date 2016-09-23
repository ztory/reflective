package com.ztory.lib.reflective;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to provide a custom namespace for your types.
 * Created by jonruna on 20/04/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReflectiveNamespace {
    String value();
}
