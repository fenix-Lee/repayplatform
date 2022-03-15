package com.hbfintech.repay.center.infrastructure.framework;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Entity {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
