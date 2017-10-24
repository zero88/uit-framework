package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.WebElement;
import com.zero.selenium.DriverBase;

public class SwitchToParentFrame extends UIAction {

  @Override
  public void run() {
    DriverBase.getDriver().switchTo().parentFrame();
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
