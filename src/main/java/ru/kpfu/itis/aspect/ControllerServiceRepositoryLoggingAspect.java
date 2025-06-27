package ru.kpfu.itis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ControllerServiceRepositoryLoggingAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.info("Controller {} method {}-Request with args: {}", className, methodName, args);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Controller {} method {} threw an exception: {}",className, methodName, e.getMessage(), e);
            throw e;
        }

        log.info("Controller {} method {}-Response: {}", className, methodName, result);
        return result;
    }

    @Around("@within(org.springframework.stereotype.Service)")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.debug("Service {} method {}-Request with args: {}", className, methodName, args);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Service {} method {} threw an exception: {}",className, methodName, e.getMessage(), e);
            throw e;
        }

        log.debug("Service {} method {}-Response: {}",className, methodName, result);
        return result;
    }

    @Around("execution(* ru.kpfu.itis.repository..*(..)) || @within(org.springframework.stereotype.Repository)")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {

        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(joinPoint.getTarget());
        String className;
        if (targetClass != null) className = targetClass.getSimpleName();
        else className = joinPoint.getTarget().getClass().getSimpleName();

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("Repository {} method {}-Request with args: {}", className, methodName, args);

        long start = System.currentTimeMillis();
        long duration = 0;

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            duration = System.currentTimeMillis() - start;
            log.error("Repository {} method {} threw an exception after {} ms: {}", className, methodName, duration, e.getMessage(), e);
            throw e;
        }
        duration = System.currentTimeMillis() - start;
        log.debug("Repository {} method {}-Response after {} ms: {}",className, methodName, duration, result);
        return result;
    }

}
