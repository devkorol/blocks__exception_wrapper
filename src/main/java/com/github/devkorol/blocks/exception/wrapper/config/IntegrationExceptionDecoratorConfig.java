package com.github.devkorol.blocks.exception.wrapper.config;

import com.github.devkorol.blocks.exception.wrapper.aspect.DecorateIntegrationExceptionAspect;
import com.github.devkorol.blocks.exception.wrapper.builder.IntegrationDefaultExceptionBuilder;
import com.github.devkorol.blocks.exception.wrapper.builder.IntegrationExceptionBuilder;
import com.github.devkorol.blocks.exception.wrapper.builder.IntegrationFeignExceptionBuilder;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnProperty(prefix = "app.blocks.exception.wrapper", name = "enable", havingValue = "true")
public class IntegrationExceptionDecoratorConfig {

  @Bean
  public IntegrationExceptionBuilder integrationDefaultExceptionBuilder() {
    return new IntegrationDefaultExceptionBuilder();
  }

  @Bean
  @Order(1)
  public IntegrationExceptionBuilder integrationFeignExceptionBuilder() {
    return new IntegrationFeignExceptionBuilder();
  }

  @Bean
  public DecorateIntegrationExceptionAspect decorateIntegrationExceptionAspect(
      List<IntegrationExceptionBuilder> builders) {
    return new DecorateIntegrationExceptionAspect(builders);
  }
}
