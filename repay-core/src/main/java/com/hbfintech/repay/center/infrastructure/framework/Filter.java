package com.hbfintech.repay.center.infrastructure.framework;

/**
 * An interface that is implemented by objects that decide if an operation
 * entry should be accepted or filtered. A {@code Filter} is passed as the
 * parameter to the method when starting a transaction to iterate over the
 * entries in the repayment process.
 *
 * This class comes from the ideal of {@code java.nio.file.DirectoryStream.Filter}
 *
 * @author Chang Su
 * @since 28/02/2022
 * @see java.nio.file.DirectoryStream.Filter
 * @param <T> filter type
 */
@FunctionalInterface
public interface Filter<T> {

    /**
     * Decides if the given directory entry should be accepted or filtered.
     *
     * @param   entry the entry to be tested
     * @return  {@code true} if the entry should be filtered
     */
    boolean accept(T entry);
}
