package com.hbfintech.repay.center.infrastructure.util;

public interface ObjectConverter<F, T> {

    T transit ();

    default F reverse (T data) {
        return null;
    }
}
