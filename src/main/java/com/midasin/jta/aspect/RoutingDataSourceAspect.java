package com.midasin.jta.aspect;

import com.midasin.jta.config.RoutingDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RoutingDataSourceAspect {

    @Around("execution(* com.midasin.jta..service.*Service.*(..))")
    public Object aroundTargetMethod(ProceedingJoinPoint thisJoinPoint) {
        log.debug("index: {}", RoutingDataSourceContextHolder.get());

        try {
            return thisJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            log.error("aspect error");
        }
    }
}