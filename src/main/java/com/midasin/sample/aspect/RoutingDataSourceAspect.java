package com.midasin.sample.aspect;

import com.midasin.sample.config.RoutingDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RoutingDataSourceAspect {

    @Around("execution(* com.midasin.sample..service.*Service.*(..))")
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
