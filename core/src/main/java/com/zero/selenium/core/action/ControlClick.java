package com.zero.selenium.core.action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.zero.selenium.DriverBase;

public final class ControlClick extends UIAction {

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    Actions actions = new Actions(DriverBase.getDriver());
    actions.moveToElement(element, 1, 1).keyDown(Keys.LEFT_CONTROL).click(element)
        .keyUp(Keys.LEFT_CONTROL).build().perform();
    return element;
  }

}
