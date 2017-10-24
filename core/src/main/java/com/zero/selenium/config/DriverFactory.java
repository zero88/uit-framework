package com.zero.selenium.config;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {

  private WebDriver webdriver;
  private DriverType selectedDriverType;

  /**
   * Gets the driver.
   *
   * @return the {@link WebDriver}
   */
  public WebDriver getDriver() {
    if (null == webdriver) {
      Proxy proxy = null;
      if (Configuration.instance().isProxyEnable()) {
        String proxyDetail = Configuration.instance().getProxyDetail();
        proxy = new Proxy();
        proxy.setProxyType(MANUAL);
        proxy.setHttpProxy(proxyDetail);
        proxy.setSslProxy(proxyDetail);
      }
      determineEffectiveDriverType();
      DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities(proxy);
      this.webdriver = instantiateWebDriver(desiredCapabilities);
    }
    return webdriver;
  }

  /**
   * Returns a WebDriverWait instance for the configured WebDriver respectively the VM command line
   * parameter <code>-Dtimeout</code>.
   * 
   * @return the instance of WebDriverWait for the configured WebDriver
   */
  public WebDriverWait getWebDriverWait() {
    WebDriver webDriver = getDriver();
    if (webDriver != null) {
      return new WebDriverWait(webDriver, Configuration.instance().getTimeout(), 1000);
    }
    return null;
  }

  /**
   * Clean WebDriver instances after run.
   */
  public void quitDriver() {
    if (null != webdriver) {
      webdriver.quit();
    }
  }

  /**
   * Handles different driver types, which are selected for the run.
   */
  private void determineEffectiveDriverType() {
    DriverType driverType = DriverType.FIREFOX;
    try {
      driverType = DriverType.valueOf(Configuration.instance().getBrowser());
    } catch (IllegalArgumentException | NullPointerException ignored) {
      LoggerFactory.instance().getConfigLog()
          .debug("Unknown driver specified, defaulting to '" + driverType + "'...", ignored);
    }
    selectedDriverType = driverType;
  }

  /**
   * Gets WebDriver instances, based on the type specified.
   *
   * @param desiredCapabilities configurations for the driver
   * @return
   */
  private WebDriver instantiateWebDriver(DesiredCapabilities desiredCapabilities) {
    if (Configuration.instance().isGridEnable()) {
      try {
        URL seleniumGridUrl = new URL(Configuration.instance().getGridHubURL());
        String desiredBrowserVersion =
            System.getProperty(Configuration.instance().getGridBrowserVersion());
        String desiredPlatform = System.getProperty(Configuration.instance().getGridNodePlatform());
        if (StringUtils.isNotEmpty(desiredPlatform)) {
          desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
        }
        if (StringUtils.isNotEmpty(desiredBrowserVersion)) {
          desiredCapabilities.setVersion(desiredBrowserVersion);
        }
        return new RemoteWebDriver(seleniumGridUrl, desiredCapabilities);
      } catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
    }
    return selectedDriverType.getWebDriverObject(desiredCapabilities);
  }
}
