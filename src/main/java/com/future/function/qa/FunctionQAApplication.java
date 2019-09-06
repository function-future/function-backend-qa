package com.future.function.qa;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.function.qa.properties.FunctionProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = FunctionProperties.class)
@ComponentScan(basePackages = {"com.future.function.qa", "net.thucydides", "net.serenitybdd"})
public class FunctionQAApplication {

  @Bean
  public ObjectMapper mapper() {

    return new ObjectMapper();
  }
}
