package com.zero.selenium.config;

import static com.zero.selenium.config.DriverType.FIREFOX;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import com.zero.selenium.DriverBase;
import com.zero.selenium.runner.Profile;
import lombok.Getter;

/**
 * This class handles the configuration of ui tests. It provides methods to retrieve specific
 * configurations properties defined in <i>configuration.properties</i>. If a property was
 * overridden using a command line parameter this overridden value will be returned instead.
 */
public class Configuration {

  public static final String DEFAULT_PROJECT_TEST = "zero";
  public static final String PROJECT_TEST = "project";
  private static final String DEFAULT_CONFIG_FILE = "configuration.properties";
  private static final String DEFAULT_DEV_CONFIG_FILE = "local-configuration.properties";
  private static final int DEFAULT_TIMEOUT = 10;

  private static final String CONFIG_FILE = "configfile";
  private static final String DEV_MODE = "dev";
  private static final String PROFILE_TEST = "profile";
  private static final String URL = "siteUrl";
  private static final String DRIVER_BROWSER = "browser";
  private static final String GRID_HUB_ENABLED = "remoteDriver";
  private static final String GRID_HUB_URL = "gridURL";
  private static final String GRID_BROWSER_VERSION = "desiredBrowserVersion";
  private static final String GRID_NODE_PLATFORM = "desiredPlatform";
  private static final String GRID_BROWSER_CHROME_ARGUMENTS = "grid.browser.chrome.arguments";
  private static final String GRID_BROWSER_FIREFOX_ARGUMENTS = "grid.browser.firefox.arguments";
  private static final String PROXY_PORT = "proxyPort";
  private static final String PROXY_HOST = "proxyHost";
  private static final String PROXY_ENABLED = "proxyEnabled";
  private static final String SCREENSHOTS_DIR = "screenshotDirectory";
  private static final String TIMEOUT = "timeout";
  private static final String FLAKY_RETRIES = "flaky.retries";
  private static final String TEST_USER = "test.user";
  private static final String TEST_PASSWORD = "test.password";

  @Getter
  private final String projectTest;
  @Getter
  private final String operatingSystem;
  @Getter
  private final String systemArchitecture;
  @Getter
  private final File reportFolder;
  private final Properties properties;

  private static Configuration instance;

  public static synchronized void initialize() {
    if (instance != null) {
      throw new IllegalStateException("Config already initialized");
    }
    instance = new Configuration();
  }

  private static synchronized void initDevConfig() {
    System.setProperty(DEV_MODE, String.valueOf(true));
    System.setProperty(PROJECT_TEST, DEFAULT_PROJECT_TEST);
    System.setProperty(CONFIG_FILE,
        Configuration.class.getClassLoader().getResource(DEFAULT_DEV_CONFIG_FILE).getPath());
    instance = new Configuration();
  }

  public static Configuration instance() {
    if (instance == null) {
      Configuration.initDevConfig();
    }
    return instance;
  }

  private Logger log() {
    return LoggerFactory.instance().getConfigLog();
  }

  private Configuration() {
    System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    System.setProperty("org.uncommons.reportng.escape-output", "false");
    this.properties = new Properties();
    String configFileName = this.loadConfigFile();
    this.operatingSystem = System.getProperty("os.name").toUpperCase();
    this.systemArchitecture = System.getProperty("os.arch");
    this.projectTest = System.getProperty(PROJECT_TEST);
    this.reportFolder =
        createFolder(Paths.get(System.getProperty("user.dir"), "target", "reports"));
    setSystemProperty("phantomjs.binary.path");
    setSystemProperty("webdriver.chrome.driver");
    setSystemProperty("webdriver.firefox.driver");
    setSystemProperty("webdriver.ie.driver");
    setSystemProperty("webdriver.opera.driver");
    setSystemProperty("webdriver.gecko.driver");
    setSystemProperty("webdriver.edge.driver");
    log().info("Successfully loaded '" + configFileName + "'");
    log().info("Current configuration (may be overwritten by defined system properties):");
    log().info("Operating System:               {}", operatingSystem);
    log().info("Architecture:                   {}", systemArchitecture);
    log().info("===============================================");
    log().info("Project:                        {}", getProjectTest());
    log().info("Profile:                        {}", getProfile());
    log().info("URL:                            {}", getURL());
    log().info("Browser:                        {}", getBrowser());
    log().info("Timeout:                        {}", getTimeout());
    log().info("Screenshots:                    {}", getScreenshotsDirectory());
    log().info("Report folder:                  {}", getReportFolder().getAbsolutePath());
    log().info("===============================================");
    log().info("Selenium grid configuration:");
    log().info("-----------------------------------------------");
    log().info("Enable:                         {}", isGridEnable());
    log().info("Grid Hub URL:                   {}", getGridHubURL());
    log().info("Browser version:                {}", getGridBrowserVersion());
    log().info("Node platform:                  {}", getGridNodePlatform());
    log().info("Chrome remote driver argument:  {}", getProperty(GRID_BROWSER_CHROME_ARGUMENTS));
    log().info("Firefox remote driver argument: {}", getProperty(GRID_BROWSER_FIREFOX_ARGUMENTS));
    log().info("===============================================");
    log().info("Proxy configuration:");
    log().info("-----------------------------------------------");
    log().info("Enable:                         {}", isProxyEnable());
    log().info("Details:                        {}", getProxyDetail());
    log().info("===============================================");
    log().info("Test User:");
    log().info("-----------------------------------------------");
    log().info("Account:                        {}", getTestUser());
    log().info("Password:                       {}", getTestPassword());
  }

  private String loadConfigFile() {
    InputStream input;
    String configFileName = System.getProperty(CONFIG_FILE, DEFAULT_CONFIG_FILE);
    if (DEFAULT_CONFIG_FILE.equals(configFileName)) {
      input = Configuration.class.getClassLoader().getResourceAsStream(configFileName);
      if (input == null) {
        throw new RuntimeException("Cannot load default config file");
      }
    } else {
      try {
        input = new FileInputStream(new File(configFileName));
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      properties.load(input);
    } catch (IOException e) {
      log().error(e.getMessage(), e);
    }
    return configFileName;
  }

  private File createFolder(Path path) {
    File reportDir = path.toFile();
    if (reportDir.exists()) {
      try {
        Files.walk(reportDir.toPath(), FileVisitOption.FOLLOW_LINKS)
            .sorted(Comparator.reverseOrder()).map(Path::toFile)
            .peek(LoggerFactory.instance().getConfigLog()::trace).forEach(File::delete);
      } catch (IOException e) {
        log().debug("Cannot clean up report folder", e);
      }
    }
    reportDir.mkdirs();
    return reportDir;
  }

  private void setSystemProperty(String property) {
    if (System.getProperty(property) == null
        && StringUtils.isNotBlank(properties.getProperty(property))) {
      System.setProperty(property, properties.getProperty(property));
    }
  }

  private String[] getWebdriverArguments(final String key) {
    final String args = getProperty(key);
    if (args != null && !args.isEmpty()) {
      return args.split(",");
    }
    return new String[0];
  }

  public Profile getProfile() {
    return Profile.parse(getProperty(PROFILE_TEST));
  }

  /**
   * Returns the URL of the Website target system that is configured in
   * <i>configuration.properties</i>. If this configuration property was overridden using the VM
   * command line parameter <code>-DsiteUrl</code> the overridden value will be returned.
   * 
   * @return the URL of the Website target system or <code>null</code> if the configuration property
   *         wasn't specified.
   */
  public String getURL() {
    return System.getProperty(URL, properties.getProperty(URL));
  }

  /**
   * Returns the browser that is configured in <i>configuration.properties</i> and should be used to
   * run the automated test on a node of the Selenium Grid. If this configuration property was
   * overridden using the VM command line parameter <code>-Dgrid.browser</code> the overridden value
   * will be returned.
   * 
   * @return the browser that should be used to run the automated test on a node of the Selenium
   *         Grid or <code>null</code> if the configuration property wasn't specified.
   */
  public String getBrowser() {
    return System.getProperty(DRIVER_BROWSER,
        properties.getProperty(DRIVER_BROWSER, FIREFOX.name()));
  }

  public boolean isGridEnable() {
    return Boolean
        .valueOf(System.getProperty(GRID_HUB_ENABLED, properties.getProperty(GRID_HUB_ENABLED)));
  }


  /**
   * Returns the URL of the Selenium Hub that is configured in <i>configuration.properties</i>. If
   * this configuration property was overridden using the VM command line parameter
   * <code>-DgridURL</code> the overridden value will be returned.
   * 
   * @return the URL of the Selenium Hub or <code>null</code> if the configuration property wasn't
   *         specified.
   */
  public String getGridHubURL() {
    return System.getProperty(GRID_HUB_URL, properties.getProperty(GRID_HUB_URL));
  }

  /**
   * Returns the version of the browser that is configured in <i>configuration.properties</i> and
   * should be used to run the automated test on a node of the Selenium Grid. If this configuration
   * property was overridden using the VM command line parameter
   * <code>-DdesiredBrowserVersion</code> the overridden value will be returned.
   * 
   * @return the version of the browser that should be used to run the automated test on a node of
   *         the Selenium Grid or <code>null</code> if the configuration property wasn't specified.
   */
  public String getGridBrowserVersion() {
    return System.getProperty(GRID_BROWSER_VERSION, properties.getProperty(GRID_BROWSER_VERSION));
  }

  /**
   * Returns the operating system that is configured in <i>configuration.properties</i> and should
   * be installed on the node of the Selenium Grid that will be used to run the automated test. If
   * this configuration property was overridden using the VM command line parameter
   * <code>-DdesiredPlatform</code> the overridden value will be returned.
   * 
   * @return the identifier of the operating system that must be installed on the node of the
   *         Selenium Grid for running the automated test or <code>null</code> if the configuration
   *         property wasn't specified.
   */
  public String getGridNodePlatform() {
    return getProperty(GRID_NODE_PLATFORM);
  }

  public boolean isProxyEnable() {
    return Boolean
        .valueOf(System.getProperty(PROXY_ENABLED, properties.getProperty(PROXY_ENABLED)));
  }

  public String getProxyDetail() {
    String proxyHostname = System.getProperty(PROXY_HOST, properties.getProperty(PROXY_HOST));
    Integer proxyPort;
    try {
      proxyPort =
          Integer.valueOf(System.getProperty(PROXY_PORT, properties.getProperty(PROXY_PORT)));
      return String.format("%s:%d", proxyHostname, proxyPort);
    } catch (NumberFormatException e) {
      log().trace("Cannot parse proxy port", e);
      return proxyHostname;
    }
  }

  public String getTestUser() {
    return System.getProperty(TEST_USER, properties.getProperty(TEST_USER));
  }

  public String getTestPassword() {
    return System.getProperty(TEST_PASSWORD, properties.getProperty(TEST_PASSWORD));
  }


  /**
   * Returns the timeout (in seconds) that is configured in <i>configuration.properties</i>. This
   * timeout will be used for explicit Selenium waits. If this configuration property was overridden
   * using the VM command line parameter <code>-Dtimeout</code> the overridden value will be
   * returned.
   * 
   * @return the timeout in seconds or the default value <code>10</code> if the configuration
   *         property wasn't specified.
   */
  public int getTimeout() {
    String timeout = getProperty(TIMEOUT, String.valueOf(DEFAULT_TIMEOUT));
    try {
      return Integer.parseInt(timeout);
    } catch (NumberFormatException e) {
      log().trace("Cannot parse timeout", e);
      return DEFAULT_TIMEOUT;
    }
  }

  /**
   * Returns the directory to store generated screenshots that is configured in
   * <i>configuration.properties</i>. Tests that extend {@link DriverBase} will create screenshots
   * on failure and store them in the specified directory. If this configuration property was
   * overridden using the VM command line parameter <code>-DscreenshotDirectory</code> the
   * overridden value will be returned.
   * 
   * @return the directory to store generated screenshots or <code>null</code> if the configuration
   *         property wasn't specified.
   */
  public String getScreenshotsDirectory() {
    return getProperty(SCREENSHOTS_DIR);
  }

  /**
   * For remote webdriver only! Returns a string array containing arguments which get passed to
   * Chrome at its program startup.
   *
   * @return Chrome browser arguments
   */
  public String[] getGridBrowserChromeArguments() {
    return getWebdriverArguments(GRID_BROWSER_CHROME_ARGUMENTS);
  }

  /**
   * For remote webdriver only! Returns a string array containing arguments which get passed to
   * Firefox at its program startup.
   *
   * @return Firefox browser arguments
   */
  public String[] getGridBrowserFirefoxArguments() {
    return getWebdriverArguments(GRID_BROWSER_FIREFOX_ARGUMENTS);
  }

  public int getFlakyComponentRetries() {
    return Integer.parseInt(getProperty(FLAKY_RETRIES, "2"));
  }

  /**
   * Returns the value of the system property with the passed name. If no system property with this
   * name has been specified the method returns the value of the property with the passed name
   * defined in the configuration properties file. Otherwise the method returns <code>null</code>.
   * 
   * @param name of the property
   * @return the value of the property
   * 
   */
  public String getProperty(String name) {
    return getProperty(name, "");
  }

  public String getProperty(String name, String defaultValue) {
    return System.getProperty(name, properties.getProperty(name, defaultValue));
  }

  public Reader getReader(String fileName) {
    try {
      return Boolean.valueOf(System.getProperty(DEV_MODE))
          ? new InputStreamReader(
              Configuration.class.getClassLoader().getResourceAsStream(fileName))
          : new FileReader(Paths.get(System.getProperty("user.dir"), fileName).toFile());
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Cannot find a file: " + fileName, e);
    }
  }

  public Path getFilePath(String fileName) {
    try {
      return Boolean.valueOf(System.getProperty(DEV_MODE))
          ? Paths.get(Configuration.class.getClassLoader().getResource(fileName).toURI())
          : Paths.get(System.getProperty("user.dir"), fileName);
    } catch (URISyntaxException e) {
      throw new RuntimeException("Cannot find a file: " + fileName, e);
    }
  }
  
}
