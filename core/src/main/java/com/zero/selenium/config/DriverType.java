package com.zero.selenium.config;

import static org.openqa.selenium.remote.CapabilityType.PROXY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public enum DriverType implements DriverSetup {

  FIREFOX {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.firefox();
      capabilities.setCapability("marionette", true);
      return addProxySettings(capabilities, proxySettings);
    }

    /**
     * Gets the web driver.
     * 
     * @param capabilities the driver desired capabilities.
     * @return the web driver
     */
    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      WebDriver firefoxDriver = new FirefoxDriver(capabilities);
      firefoxDriver.manage().window().maximize();
      return firefoxDriver;
    }
  },
  CHROME {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability("chrome.switches",
          Collections.singletonList("--no-default-browser-check"));

      HashMap<String, String> chromePreferences = new HashMap<>();
      chromePreferences.put("profile.password_manager_enabled", "false");
      capabilities.setCapability("chrome.prefs", chromePreferences);
      return addProxySettings(capabilities, proxySettings);
    }

    /**
     * Gets the web driver.
     * 
     * @param capabilities the driver desired capabilities.
     * @return the web driver
     */
    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.addArguments("--verbose");
      chromeOptions.addArguments("--whitelisted-ips=''");
      chromeOptions.addArguments("--no-sandbox");
      chromeOptions.addArguments("--start-maximized");
      chromeOptions.addArguments("--disable-infobars");
      return new ChromeDriver(chromeOptions);
    }
  },
  IE {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
      capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
      capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
      capabilities.setCapability("requireWindowFocus", true);
      return addProxySettings(capabilities, proxySettings);
    }

    /**
     * Gets the web driver.
     * 
     * @param capabilities the driver desired capabilities.
     * @return the web driver
     */
    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      return new InternetExplorerDriver(capabilities);
    }
  },
  EDGE {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.edge();
      return addProxySettings(capabilities, proxySettings);
    }

    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      return new EdgeDriver(capabilities);
    }
  },
  SAFARI {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.safari();
      capabilities.setCapability("safari.cleanSession", true);
      return addProxySettings(capabilities, proxySettings);
    }

    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      return new SafariDriver(capabilities);
    }
  },
  OPERA {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
      return addProxySettings(capabilities, proxySettings);
    }

    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      return new OperaDriver(capabilities);
    }
  },
  PHANTOMJS {
    /**
     * Gets the desired driver capabilities.
     * 
     * @param proxySettings configurations for the driver
     * @return the desired driver capabilities.
     */
    @Override
    public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
      final List<String> cliArguments = new ArrayList<>();
      cliArguments.add("--web-security=false");
      cliArguments.add("--ssl-protocol=any");
      cliArguments.add("--ignore-ssl-errors=true");
      DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
      capabilities.setCapability("phantomjs.cli.args",
          applyPhantomJsProxySettings(cliArguments, proxySettings));
      capabilities.setCapability("takesScreenshot", true);

      return capabilities;
    }

    @Override
    public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      return new PhantomJSDriver(capabilities);
    }
  };

  /**
   * Sets specified Proxy configurations.
   *
   * @param capabilities configurations for the driver
   * @param proxySettings proxy specific configurations for the driver
   * @return Updated configurations for the driver
   */
  protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities,
      Proxy proxySettings) {
    if (null != proxySettings) {
      capabilities.setCapability(PROXY, proxySettings);
    }
    return capabilities;
  }

  /**
   * Sets specific to PhantomJS Proxy configurations.
   *
   * @param cliArguments configurations for the driver
   * @param proxySettings proxy specific configurations for the driver
   * @return Updated configurations for the driver
   */
  protected List<String> applyPhantomJsProxySettings(List<String> cliArguments,
      Proxy proxySettings) {
    if (null == proxySettings) {
      cliArguments.add("--proxy-type=none");
    } else {
      cliArguments.add("--proxy-type=http");
      cliArguments.add("--proxy=" + proxySettings.getHttpProxy());
    }
    return cliArguments;
  }
}
