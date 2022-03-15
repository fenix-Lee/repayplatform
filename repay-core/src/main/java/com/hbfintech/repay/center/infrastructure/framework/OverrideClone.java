package com.hbfintech.repay.center.infrastructure.framework;

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
public @interface OverrideClone {

    /**
     *
     *
     * @return true if cloneable function is enabled
     */
    boolean value() default true;
}
