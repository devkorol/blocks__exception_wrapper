package com.github.devkorol.blocks.exception.wrapper.builder;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationFeignException;
import feign.FeignException;

public class IntegrationFeignExceptionBuilder implements IntegrationExceptionBuilder {

  @Override
  public Class<? extends Throwable>[] supportedExceptions() {
    return new Class[]{FeignException.class};
  }

  @Override
  public IntegrationException build(Exception ex, DecorateIntegrationException annotation) {
    int statusCode = ((FeignException) ex).status();
    String serviceName = annotation.value();

    return new IntegrationFeignException(
        statusCode,
        serviceName,
        String.format("Unknown exception while working with service <%s> with code <%s>", serviceName, statusCode),
        ex
    );
  }
}
