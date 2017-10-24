package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.zero.selenium.DriverBase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SetText extends UIAction {

  private final String text;

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    element.clear();
    element.sendKeys(this.text);
    return element;
  }

}
