package com.ihorshulha.asyncapidatamanager.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ExecutionTimeAdvice.class);

    @Around("@annotation(com.ihorshulha.asyncapidatamanager.util.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.nanoTime();
        Object object = point.proceed();
        long endTime = System.nanoTime();
        logger.info(MessageFormat.format("Method Name: {0}. Thread {1}. Time taken for execution is : {2}seconds",
                point.getSignature().getName(), Thread.currentThread().getId(),
                (double) (endTime - startTime) / 1_000_000_000.0));
        return object;
    }
}
