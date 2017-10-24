package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.zero.selenium.DriverBase;

public final class VerifyElement extends UIAction {

  @Override
  protected WebElement doRun(String xpath) {
    throw new UnsupportedOperationException();
  }

  public boolean validate(String xpath) {
    return DriverBase.getDriverWait()
        .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
  }

}
