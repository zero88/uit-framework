package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.WebElement;
import com.zero.selenium.DriverBase;

public final class SwitchToDefaultContent extends UIAction {

  @Override
  public void run() {
    DriverBase.getDriver().switchTo().defaultContent();
  }

  @Override
  protected WebElement doRun(String xpath) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<WebElement> run(String... xpaths) {
    throw new UnsupportedOperationException();
  }

}
