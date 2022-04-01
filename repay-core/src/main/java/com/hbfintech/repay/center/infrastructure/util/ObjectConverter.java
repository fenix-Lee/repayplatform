package com.hbfintech.repay.center.infrastructure.util;

public interface ObjectConverter<T> {

    default T transit () {
        return null;
    }

    default void transit(T target) {}

    /**
     * save data in warehouse
     */
    default void save() {}
}
