package com.github.devkorol.blocks.exception.wrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = "com.github.devkorol.blocks.exception.wrapper"
)
public class SpringApplicationTest {

  public static void main(String[] args) {
    SpringApplication.run(SpringApplicationTest.class, args);
  }
}