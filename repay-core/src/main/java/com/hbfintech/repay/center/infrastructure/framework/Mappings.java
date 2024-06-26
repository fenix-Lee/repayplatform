package com.hbfintech.repay.center.infrastructure.framework;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Mappings {

    FieldMapping[] value();
}
