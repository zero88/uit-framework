package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.zero.selenium.DriverBase;

public final class DoubleClick extends UIAction {

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    Actions actions = new Actions(DriverBase.getDriver());
    actions.moveToElement(element, 1, 1).doubleClick(element).build().perform();
    return element;
  }

}
