package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.zero.selenium.DriverBase;

public final class Click extends UIAction implements IActionList {

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    return click(element);
  }

  @Override
  public List<WebElement> multipleRun(String xpath) {
    List<WebElement> elements = DriverBase.getDriver().findElements(By.xpath(xpath));
    elements.forEach(element -> {
      retry(() -> {
        return click(element);
      });
    });
    return elements;
  }

  private WebElement click(WebElement element) {
    Actions actions = new Actions(DriverBase.getDriver());
    actions.moveToElement(element).click().perform();
    return element;
  }
}
