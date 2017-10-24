package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.zero.selenium.DriverBase;

public final class SwitchToIFrame extends UIAction {

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriverWait()
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    DriverBase.getDriver().switchTo().frame(element);
    return element;
  }

}
