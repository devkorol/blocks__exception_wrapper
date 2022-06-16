package com.github.devkorol.blocks.exception.wrapper.exception;

import lombok.Getter;

@Getter
public class IntegrationFeignException extends IntegrationException {

  /**
   * Returned HTTP status code.
   */
  private final Integer statusCode;

  public IntegrationFeignException(int statusCode, String serviceName, String message, Throwable e) {
    super(serviceName, message, e);
    this.statusCode = statusCode;
  }
}
