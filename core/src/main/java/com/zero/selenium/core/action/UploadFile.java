package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.zero.selenium.DriverBase;
import com.zero.selenium.config.Configuration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFile extends UIAction implements IAction {

  private final String filePath;

  @Override
  protected WebElement doRun(String xpath) {
    WebDriver driver = DriverBase.getDriver();
    if (Configuration.instance().isGridEnable()) {
      ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
    }
    WebElement element = driver.findElement(By.xpath(xpath));
    element.sendKeys(this.filePath);
    return element;
  }

}
