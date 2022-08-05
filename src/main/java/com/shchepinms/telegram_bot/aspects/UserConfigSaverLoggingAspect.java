package com.shchepinms.telegram_bot.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class UserConfigSaverLoggingAspect {

    @Pointcut("execution(* com.shchepinms.telegram_bot.util.UserConfigManager.*(..))")
    public void allUserConfigSaverMethods() {}

    @Around("allUserConfigSaverMethods()")
    public Object allMethodsLoggingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.err.printf("%n   >>START  %s %s%n", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
        Object result  = joinPoint.proceed(joinPoint.getArgs());
        System.err.printf("%n   <<END    %s%n", joinPoint.getSignature().getName());
        return result;
    }
}
