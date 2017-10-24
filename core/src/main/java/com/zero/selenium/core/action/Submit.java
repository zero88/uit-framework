package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.zero.selenium.DriverBase;

public final class Submit extends UIAction {

  @Override
  public List<WebElement> run(String... xpaths) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected WebElement doRun(String xpath) {
    WebElement element = DriverBase.getDriver().findElement(By.xpath(xpath));
    element.submit();
    return element;
  }

}
