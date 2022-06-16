package com.github.devkorol.blocks.exception.wrapper.aspect;

import com.github.devkorol.blocks.exception.wrapper.DecorateIntegrationException;
import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component
public class StubClass {

  public static final String NAME = "stub-class";
  public static final int STATUS_CODE = 500;

  @DecorateIntegrationException(NAME)
  public String integrationMethod() {
    return "Always success";
  }

  @DecorateIntegrationException(NAME)
  public String integrationMethodFailRuntime() {
    throw new RuntimeException("Always fail");
  }

  @DecorateIntegrationException(NAME)
  public String integrationMethodFailFeign() {
    throw FeignException.errorStatus(
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
}
