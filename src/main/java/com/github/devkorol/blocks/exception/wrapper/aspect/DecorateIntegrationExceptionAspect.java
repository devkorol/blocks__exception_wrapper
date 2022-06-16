package com.github.devkorol.blocks.exception.wrapper.aspect;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.builder.IntegrationExceptionBuilder;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * DecorateIntegrationExceptionAspect - common logic for handling exception from external calls.
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class DecorateIntegrationExceptionAspect {

  private final List<IntegrationExceptionBuilder> builders;

  /**
   * Wrap around method invocation with marked annotation WrapIntegrationException on top.
   *
   * @param joinPoint - aspect execution context.
   * @return method invocation result.
   */
  @Around("@annotation(com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException)")
  public Object doRecoveryActions(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      return joinPoint.proceed();
    } catch (Exception e) {
      log.debug("Faced exception {} trying wrap it in app friendly exception", e.getClass());
      throw buildException(joinPoint, e);
    } catch (Throwable e) {
      //re-throw all non-exception class problems;
      throw e;
    }
  }

  private IntegrationException buildException(ProceedingJoinPoint joinPoint, Exception e) {
    DecorateIntegrationException annotation = getDeclaredAnnotation(joinPoint);
    IntegrationExceptionBuilder builder = getBuilderByExceptionClass(e);
    log.debug("For exception {} will be used builder {}", e.getClass(), builder);
    return builder.build(e, annotation);
  }

  private DecorateIntegrationException getDeclaredAnnotation(ProceedingJoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getMethod()
        .getAnnotation(DecorateIntegrationException.class);
  }

  private IntegrationExceptionBuilder getBuilderByExceptionClass(Exception e) {
    for (IntegrationExceptionBuilder builder : builders) {
      Class<? extends Throwable>[] supportedExceptions = builder.supportedExceptions();
      if (supportedExceptions == null
          || supportedExceptions.length == 0) {
        continue;
      }

      for (Class<? extends Throwable> supportedException : supportedExceptions) {
        if (supportedException.isAssignableFrom(e.getClass())) {
          return builder;
        }
      }
    }

    throw new RuntimeException("Cant find any exception builder for class " + e.getClass(), e);
  }
}
