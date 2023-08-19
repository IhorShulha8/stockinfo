package com.ihorshulha.asyncapidatamanager.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

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
        logger.info("Class Name: " + point.getSignature().getDeclaringTypeName() + ". Method Name: " +
                point.getSignature().getName() + ". Time taken for Execution is : " +
                (double) (endTime - startTime) / 1_000_000_000.0 + "seconds");
        return object;
    }
}
