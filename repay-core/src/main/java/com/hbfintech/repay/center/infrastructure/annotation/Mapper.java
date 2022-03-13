package com.hbfintech.repay.center.infrastructure.annotation;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 *
 * @author Chang Su
 * @since 26/02/2022
 * @see Mappers
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Validated
@Repeatable(Mappers.class)
public @interface Mapper {

    @NotNull
    Class<?> target();
}
