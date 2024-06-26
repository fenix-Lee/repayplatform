package com.hbfintech.repay.center.infrastructure.framework;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * This annotation indicates meta information of which module is being used under specific chain class.
 * Multiple-chain are allowed by {@link Chains} annotation.
 *
 * Remember that the sequence field is also mandatory and DO NOT use same order between modules in same
 * chain class
 *
 * @author Chang Su
 * @since 2020/7/16
 * @see Chains
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
@Repeatable(Chains.class)
public @interface Chain {

    /*
     * indicates modules by which specific chain class are used. If this module is referred to
     * other chain class, multiple annotations should be claimed on top of module class.
     *
     * @return chain class
     */
    Class<?> chain();

    /*
     * indicates modules under which position are used
     *
     * @return module rank
     */
    int sequence() default 0;
}
