package com.github.devkorol.blocks.exception.wrapper.builder;


import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;

public class IntegrationDefaultExceptionBuilder implements IntegrationExceptionBuilder {

  @Override
  public Class<? extends Throwable>[] supportedExceptions() {
    return new Class[]{Exception.class};
  }

  @Override
  public IntegrationException build(Exception ex, DecorateIntegrationException annotation) {
    String serviceName = annotation.value();

    return new IntegrationException(
        serviceName,
        String.format("Unknown exception while working with service <%s>", serviceName),
        ex
    );
  }
}
