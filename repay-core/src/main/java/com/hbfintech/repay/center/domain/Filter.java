package com.hbfintech.repay.center.domain;

/**
 *
 * @author Chang Su
 * @since 28/02/2022
 * @see java.nio.file.DirectoryStream.Filter
 * @param <T>
 */
@FunctionalInterface
public interface Filter<T> {

    boolean accept(T entry);
}
