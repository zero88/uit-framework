package com.zero.selenium.config;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverSetup {

  /**
   * Gets WebDriver with specfified configurations.
   *
   * @param desiredCapabilities configurations for the driver
   * @return Updated driver with configurations for its type
   */
  WebDriver getWebDriverObject(DesiredCapabilities desiredCapabilities);

  /**
   * Gets the specfic configurations for the driver.
   *
   * @param proxySettings configurations for the driver
   * @return Updated configurations for the driver
   */
  DesiredCapabilities getDesiredCapabilities(Proxy proxySettings);
}
