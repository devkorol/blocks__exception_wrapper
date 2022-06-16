package com.github.devkorol.blocks.exception.wrapper.builder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationDefaultExceptionBuilderTest {

  private static final String SERVICE_NAME = "ms-mdm-data";

  private IntegrationDefaultExceptionBuilder builder;
  private DecorateIntegrationException annotation;

  @BeforeEach
  void setUp() {
    builder = new IntegrationDefaultExceptionBuilder();
    annotation = mock(DecorateIntegrationException.class);
  }

  @Test
  void supportedExceptions() {
    Class<? extends Throwable>[] exceptions = builder.supportedExceptions();
    assertArrayEquals(new Class[]{Exception.class}, exceptions);
  }

  @Test
  void build() {
    when(annotation.value()).thenReturn(SERVICE_NAME);
    NullPointerException ex = new NullPointerException();

    IntegrationException exception = builder.build(
        ex,
        annotation
    );

    assertEquals(SERVICE_NAME, exception.getServiceName(),
        "Service name should be presented in built exception and must be equals to annotation value");
    assertEquals(ex, exception.getCause(),
        "Root exception should be presented as cause");
  }
}