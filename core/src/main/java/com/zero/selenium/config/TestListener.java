package com.zero.selenium.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.zero.selenium.DriverBase;

public class TestListener implements ITestListener {

  private static final Logger LOG = LoggerFactory.instance().getExecutionLog();

  @Override
  public void onFinish(ITestContext testContext) {}

  @Override
  public void onStart(ITestContext testContext) {
    Configuration.instance();
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onTestFailure(ITestResult result) {
    String outputDir = Configuration.instance().getReportFolder().toPath().resolve("html")
        .resolve(Configuration.instance().getScreenshotsDirectory()).toString();
    WebDriver driver = DriverBase.getDriver();
    if (!outputDir.isEmpty() && driver != null) {
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      String fileName = result.getName() + "_" + System.currentTimeMillis() + ".png";
      Path relativePath = Paths.get(Configuration.instance().getScreenshotsDirectory(), fileName);
      File outputFile = new File(outputDir, fileName);
      try {
        FileUtils.copyFile(scrFile, outputFile);
        Reporter.log("<p>SCREENSHOT: </p>");
        Reporter.log("<p><img width='1024' src='" + relativePath.toString()
            + "' alt='screenshot at " + result.getName() + "'/></p></a><br />");
        LOG.info("Screenshot created: " + outputFile.getName());
      } catch (IOException ex) {
        LOG.info("Failed to create screenshot", ex);
      }
    } else {
      LOG.info(
          "Didn't create screenshot on failure because no screenshot directory is configured.");
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {

  }

  @Override
  public void onTestStart(ITestResult result) {
    LOG.info("=========================================================================");
    LOG.info("Test Class    : " + result.getInstanceName());
    LOG.info("Method        : " + result.getName());
    Object[] parameters = result.getParameters();
    if (parameters != null && parameters.length > 0) {
      LOG.info("Testcase      : " + parameters[0]);
    }
    LOG.info("-------------------------------------------------------------------------");
  }

  @Override
  public void onTestSuccess(ITestResult result) {}

}
