package com.ihorshulha.asyncapidatamanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface IgnoreRuntimeException {

    Logger logger = LoggerFactory.getLogger(IgnoreRuntimeException.class);

    void run() throws RuntimeException;

    static void ignoredException(IgnoreRuntimeException exception) {
        try {
            exception.run();
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
        }
    }
}
