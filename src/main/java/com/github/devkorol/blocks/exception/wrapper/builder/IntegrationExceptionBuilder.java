package com.github.devkorol.blocks.exception.wrapper.builder;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;

public interface IntegrationExceptionBuilder {

  /**
   * Describe supported exceptions by it builder.
   *
   * @return list of exception that will be parsed by it builder into {@link IntegrationException}
   */
  Class<? extends Throwable>[] supportedExceptions();

  /**
   * Handle exception and build local exception instance.
   *
   * @param ex         handled exception
   * @param annotation filled annotation instance {@link DecorateIntegrationException}
   * @return app exception instance.
   */
  IntegrationException build(Exception ex, DecorateIntegrationException annotation);
}
