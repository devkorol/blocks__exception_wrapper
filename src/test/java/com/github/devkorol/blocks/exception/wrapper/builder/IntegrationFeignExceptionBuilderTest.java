package com.github.devkorol.blocks.exception.wrapper.builder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationFeignException;
import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationFeignExceptionBuilderTest {

  public static final int STATUS_CODE = 500;
  private static final String SERVICE_NAME = "ms-mdm-data";
  private IntegrationFeignExceptionBuilder builder;
  private DecorateIntegrationException annotation;
  private FeignException ex;

  @BeforeEach
  void setUp() {
    builder = new IntegrationFeignExceptionBuilder();
    annotation = mock(DecorateIntegrationException.class);

    ex = FeignException.errorStatus(
        "some_method",
        Response.builder()
            .status(STATUS_CODE)
            .body(new byte[]{})
            .headers(Collections.EMPTY_MAP)
            .reason("Internal server error")
            .request(Request.create(
                HttpMethod.GET,
                "http://domain.com",
                Collections.EMPTY_MAP,
                new byte[]{},
                StandardCharsets.UTF_8
            ))
            .build()
    );
  }

  @Test
  void supportedExceptions() {
    Class<? extends Throwable>[] exceptions = builder.supportedExceptions();
    assertArrayEquals(new Class[]{FeignException.class}, exceptions);
  }

  @Test
  void build() {
    when(annotation.value()).thenReturn(SERVICE_NAME);

    IntegrationFeignException exception = (IntegrationFeignException) builder.build(
        ex,
        annotation
    );

    assertEquals(SERVICE_NAME, exception.getServiceName(),
        "Service name should be presented in built exception and must be equals to annotation value");
    assertEquals(STATUS_CODE, exception.getStatusCode(),
        "Status code should be presented in built exception and must be equals to feighException status");
    assertEquals(ex, exception.getCause(),
        "Root exception should be presented as cause");
  }
}