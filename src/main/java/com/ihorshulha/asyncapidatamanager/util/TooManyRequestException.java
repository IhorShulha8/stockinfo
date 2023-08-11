package com.ihorshulha.asyncapidatamanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface TooManyRequestException {

    Logger logger = LoggerFactory.getLogger(TooManyRequestException.class);

    void run() throws RuntimeException;

    static void ignoredException(TooManyRequestException exception) {
        try {
            exception.run();
        } catch (RuntimeException ignored) {
            logger.info(ignored.getMessage());
        }
    }
}
