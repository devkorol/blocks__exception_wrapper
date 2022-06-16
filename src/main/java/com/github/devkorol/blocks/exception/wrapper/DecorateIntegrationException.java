package com.github.devkorol.blocks.exception.wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DecorateIntegrationException - marked annotation for methods which calls external services and needs to be wrapped
 * with try-catch logic for correct exception handling.
 *
 * @author Kirill_Korol
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecorateIntegrationException {

    /**
     * Name of the external service which calls in method. Example "ms-my-service"
     */
    String value();
}
