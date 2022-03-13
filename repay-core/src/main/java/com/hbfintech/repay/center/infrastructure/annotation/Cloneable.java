package com.hbfintech.repay.center.infrastructure.annotation;

import java.lang.annotation.*;

/**
 *
 * @author Chang Su
 * @since 10/03/2022
 * @see java.lang.Cloneable
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cloneable {

    /**
     *
     *
     * @return true if cloneable function is enabled
     */
    boolean value() default true;
}
