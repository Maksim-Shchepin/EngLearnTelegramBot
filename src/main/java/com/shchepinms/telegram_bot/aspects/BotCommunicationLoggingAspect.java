package com.shchepinms.telegram_bot.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Aspect
public class BotCommunicationLoggingAspect {

    @Pointcut("execution(* *(..))")
    public void allMethods() {}

    @Pointcut("execution(public void processNonCommandUpdate(..))")
    public void processNonCommandUpdate() {}

    @Pointcut("execution(public void processMessage(..))")
    public void processMessage() {}

    @Around("processNonCommandUpdate()")
    public Object nonCommandLoggingAdvice(ProceedingJoinPoint joinPoint) {
        System.err.printf("%n   >>START  %s %s%n", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
        Object result;
        try {
            Update update = (Update) joinPoint.getArgs()[0];
            if (update.hasMessage()) {
                System.err.printf("Incoming from %d, message: %s%n", update.getMessage().getFrom().getId(), update.getMessage().getText());
            }
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.err.printf("%n   <<END    %s%n", joinPoint.getSignature().getName());
        return result;
    }

        @Around("processMessage()")
    public Object commandLoggingAdvice(ProceedingJoinPoint joinPoint) {
            System.err.printf("%n   >>START  %s %s%n", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
        Object result;
        try {
            Message message = (Message) joinPoint.getArgs()[1];
            System.err.printf("Incoming from %d, message: %s%n", message.getFrom().getId(), message.getText());
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.err.printf("%n   <<END    %s%n", joinPoint.getSignature().getName());
        return result;
    }

/*    @Around("allMethods()")
    public Object allMethodsLoggingAdvice(ProceedingJoinPoint joinPoint) {
        System.err.printf("%n   >>START  %s%n", joinPoint.getSignature().getName());
        Object result;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.err.printf("%n   <<END    %s%n", joinPoint.getSignature().getName());
        return result;
    }*/

}
