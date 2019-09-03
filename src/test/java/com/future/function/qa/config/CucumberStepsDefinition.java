package com.future.function.qa.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.future.function.qa.FunctionQAApplication;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = FunctionQAApplication.class)
@ContextConfiguration(classes = FunctionQAApplication.class)
public @interface CucumberStepsDefinition {}
