package com.zero.selenium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.zero.selenium.config.DriverFactory;
import com.zero.selenium.config.LoggerFactory;
import com.zero.selenium.config.TestListener;

@Listeners(value = TestListener.class)
public class DriverBase {

  public final static String SMOKE_GROUP = "smoke";
  public final static String REGRESSION_GROUP = "regression";

  private static List<DriverFactory> webDriverThreadPool =
      Collections.synchronizedList(new ArrayList<DriverFactory>());
  private static ThreadLocal<DriverFactory> driverFactory;

  /**
   * Instantiates the {@link DriverFactory}.
   */
  @BeforeMethod(alwaysRun = true)
  public static void instantiateDriverObject() {
    driverFactory = ThreadLocal.withInitial(() -> {
      DriverFactory driverFactory = new DriverFactory();
      webDriverThreadPool.add(driverFactory);
      return driverFactory;
    });
  }

  public static WebDriver getDriver() {
    return driverFactory.get().getDriver();
  }

  public static WebDriverWait getDriverWait() {
    return driverFactory.get().getWebDriverWait();
  }

  @AfterMethod(alwaysRun = true)
  public static void tearDown() throws Exception {
    getDriver().close();
  }

  /**
   * Quit the drivers.
   */
  @AfterSuite(alwaysRun = true)
  public static void closeDriverObjects() {
    for (DriverFactory driverFactory : webDriverThreadPool) {
      driverFactory.quitDriver();
    }
  }

  protected final void sleep(int milisecond) {
    try {
      Thread.sleep(milisecond);
    } catch (InterruptedException e) {
      LoggerFactory.instance().getExecutionLog().debug("Failure when sleeping", e);
    }
  }

  protected final void sleep() {
    sleep(2000);
  }

  protected String randomName() {
    return randomName(10);
  }

  protected String randomName(int length) {
    return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
  }

  protected Logger log() {
    return LoggerFactory.instance().getExecutionLog();
  }
}
