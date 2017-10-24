package com.zero.selenium.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Getter;

@Getter
public class LoggerFactory {

  private static LoggerFactory loggerFactory;
  private final static String PREFIX = "ui_test.";
  private final static String SELENIUM_LOGGER_NAME = "selenium";
  private final static String COMPONENT_LOGGER_NAME = "component";
  private final static String PAGE_LOGGER_NAME = "page";
  private final static String CONFIG_LOGGER_NAME = "config";
  private final static String FORM_LOGGER_NAME = "form";
  private final static String EXECUTION_LOGGER_NAME = "execution";
  private final Logger seleniumLog;
  private final Logger pageLog;
  private final Logger componentLog;
  private final Logger configLog;
  private final Logger executionLog;
  private final Logger formLog;

  private LoggerFactory() {
    String prefix = PREFIX
        + System.getProperty(Configuration.PROJECT_TEST, Configuration.DEFAULT_PROJECT_TEST) + ".";
    this.seleniumLog = LogManager.getLogger(prefix + SELENIUM_LOGGER_NAME);
    this.componentLog = LogManager.getLogger(prefix + COMPONENT_LOGGER_NAME);
    this.pageLog = LogManager.getLogger(prefix + PAGE_LOGGER_NAME);
    this.configLog = LogManager.getLogger(prefix + CONFIG_LOGGER_NAME);
    this.formLog = LogManager.getLogger(prefix + FORM_LOGGER_NAME);
    this.executionLog = LogManager.getLogger(prefix + EXECUTION_LOGGER_NAME);
  }

  private static synchronized void initialize() {
    if (loggerFactory != null) {
      throw new IllegalStateException("Logger factory already initialized");
    }
    loggerFactory = new LoggerFactory();
  }

  public static LoggerFactory instance() {
    if (loggerFactory == null) {
      initialize();
    }
    return loggerFactory;
  }

}
