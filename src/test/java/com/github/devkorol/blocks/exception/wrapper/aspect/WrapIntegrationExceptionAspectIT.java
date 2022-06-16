package com.github.devkorol.blocks.exception.wrapper.aspect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.devkorol.blocks.exception.wrapper.SpringApplicationTest;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationException;
import com.github.devkorol.blocks.exception.wrapper.exception.IntegrationFeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringApplicationTest.class)
class WrapIntegrationExceptionAspectIT {

  @Autowired
  private StubClass stubClassInstance;

  @Test
  void testIntegrationExceptionDecorator() {
    String result = stubClassInstance.integrationMethod();
    assertNotNull(result);
  }

  @Test
  void testIntegrationExceptionDecorator_Runtime() {
    try {
      stubClassInstance.integrationMethodFailRuntime();
    } catch (Exception e) {
      assertTrue(e instanceof IntegrationException,
          "Generic exceptions should be wrapped by IntegrationException.class");
      assertTrue(e.getMessage().contains(StubClass.NAME),
          "exception message should contains service name from annotation attribute");
    }
  }

  @Test
  void testIntegrationExceptionDecorator_Feign() {
    try {
      stubClassInstance.integrationMethodFailFeign();
    } catch (Exception e) {
      assertTrue(e instanceof IntegrationFeignException,
          "Feign exceptions should be wrapped by IntegrationFeignException.class");
      assertTrue(e.getMessage().contains(StubClass.NAME),
          "exception message should contains service name from annotation attribute");
      assertEquals(StubClass.STATUS_CODE, (int) ((IntegrationFeignException) e).getStatusCode(),
          "exception message should contains status code for feign exceptions");
    }
  }
}