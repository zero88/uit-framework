package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.zero.selenium.DriverBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelectItem extends UIAction {

  private final String text;

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    Select dropDown = new Select(element);
    dropDown.selectByVisibleText(this.text);
    return element;
  }

}
