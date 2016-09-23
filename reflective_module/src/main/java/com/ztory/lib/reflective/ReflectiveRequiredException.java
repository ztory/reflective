package com.ztory.lib.reflective;

/**
 * Exception type that will be thrown when validation fails for an instance using the
 * ReflectiveRequired annotation.
 * Created by jonruna on 20/04/16.
 */
public class ReflectiveRequiredException extends Exception {

    public ReflectiveRequiredException(String detailMessage) {
        super(detailMessage);
    }

    public ReflectiveRequiredException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
