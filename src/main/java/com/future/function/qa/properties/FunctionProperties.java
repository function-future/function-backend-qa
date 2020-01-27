package com.future.function.qa.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("function")
public class FunctionProperties {

  private String cookieName;

  private String host;

  private String port;

  private Map<String, Object> database;
}
