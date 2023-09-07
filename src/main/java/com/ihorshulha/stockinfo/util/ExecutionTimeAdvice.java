package com.ihorshulha.stockinfo.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(com.ihorshulha.stockinfo.util.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.nanoTime();
        Object object = point.proceed();
        long endTime = System.nanoTime();
        log.info(MessageFormat.format("Method Name: {0}. Thread {1}. Time taken for execution is : {2}seconds",
                point.getSignature().getName(), Thread.currentThread().getId(),
                (double) (endTime - startTime) / 1_000_000_000.0));
        return object;
    }
}
