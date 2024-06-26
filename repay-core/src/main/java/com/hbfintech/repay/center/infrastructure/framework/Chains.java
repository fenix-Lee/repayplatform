package com.hbfintech.repay.center.infrastructure.framework;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Several {@link Chain} annotations are bound in.
 *
 * @author Chang Su
 * @since 2020/7/16
 * @see java.lang.annotation.Repeatable
 * @see Chain
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface Chains {

    /**
     * contains all chain info for specific module
     *
     * @return all Chain info
     */
    Chain[] value();
}
