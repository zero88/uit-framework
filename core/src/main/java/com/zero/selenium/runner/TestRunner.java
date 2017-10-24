package com.zero.selenium.runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.testng.ITestNGListener;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.FailurePolicy;
import org.testng.xml.XmlTest;
import com.zero.selenium.config.Configuration;
import com.zero.selenium.config.LoggerFactory;

public final class TestRunner {

  private static final String REPORT_XML = "report.xml";
  private final String javaPackageTest;

  public TestRunner() {
    this(Configuration.DEFAULT_PROJECT_TEST, "com.zero.selenium.tests");
  }

  public TestRunner(String testProject, String javaPackageTest) {
    System.setProperty(Configuration.PROJECT_TEST, testProject);
    this.javaPackageTest = javaPackageTest;
    Configuration.initialize();
  }

  public void execute() {
    Profile profile = Configuration.instance().getProfile();
    ITestNGListener listener = new TestListenerAdapter();
    TestNG testng = new TestNG();
    testng.setConfigFailurePolicy(FailurePolicy.CONTINUE);
    String suiteName = Configuration.instance().getProjectTest() + "." + profile.getSuite();
    String testName = suiteName + "-test";
    XmlSuite xmlSuite = getSuite(suiteName, testName,
        profile == Profile.FULL ? new ArrayList<>() : Arrays.asList(profile.getSuite()));
    testng.setXmlSuites(Arrays.asList(xmlSuite));
    testng.addListener(listener);
    Path outPath = Configuration.instance().getReportFolder().toPath();
    testng.setOutputDirectory(outPath.toString());
    testng.run();
    processTestReport(outPath, suiteName, testName);
  }

  private XmlSuite getSuite(String suiteName, String testName, List<String> includeGroups) {
    XmlSuite xmlSuite = new XmlSuite();
    xmlSuite.setName(suiteName);
    xmlSuite.addListener("org.uncommons.reportng.HTMLReporter");
    xmlSuite.addListener("org.uncommons.reportng.JUnitXMLReporter");

    XmlTest xmlTest = new XmlTest();
    xmlTest.setName(testName);

    XmlPackage xmlPackage = new XmlPackage();
    xmlPackage.setName(this.javaPackageTest);

    xmlTest.setPackages(Arrays.asList(xmlPackage));
    xmlTest.setSuite(xmlSuite);

    if (!includeGroups.isEmpty()) {
      xmlTest.setIncludedGroups(includeGroups);
    }

    xmlSuite.setTests(Arrays.asList(xmlTest));
    return xmlSuite;
  }

  /**
   * Rename TestNg report to standard name to delivery junit report.
   * 
   * @param outputPath
   * @param suiteName
   * @param testName
   */
  private void processTestReport(Path outputPath, String suiteName, String testName) {
    try {
      File srcFile = outputPath.resolve(suiteName).resolve(testName + ".xml").toFile();
      File targetFile = outputPath.resolve(REPORT_XML).toFile();
      FileUtils.copyFile(srcFile, targetFile);
    } catch (IOException e) {
      LoggerFactory.instance().getConfigLog().error("Failure to process test report", e);
    }
  }

  public static void main(String[] args) {
    new TestRunner().execute();
  }
}
