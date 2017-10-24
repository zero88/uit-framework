package com.zero.selenium.core.action;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.zero.selenium.config.LoggerFactory;

abstract class UIAction implements IAction {

  protected static final Logger LOG = LoggerFactory.instance().getSeleniumLog();
  protected final List<WebElement> elements = new ArrayList<>();

  @Override
  public void run() {
    throw new UnsupportedOperationException();
  }

  @Override
  public final WebElement run(String xpath) {
    LOG.trace("Action " + this.getClass().getSimpleName() + " | XPath: " + xpath);
    return retry(() -> {
      return doRun(xpath);
    });
  }

  @Override
  public List<WebElement> run(String... xpaths) {
    for (String xpath : xpaths) {
      elements.add(this.run(xpath));
    }
    return elements;
  }

  protected abstract WebElement doRun(String xpath);

  protected <X> X retry(Supplier<X> code) {
    int flakyComponentRetries = 2;
    RuntimeException exception = null;
    for (int i = 1; i < flakyComponentRetries + 1; i++) {
      try {
        return code.get();
      } catch (Throwable e) {
        String message = "Operation failed. Attempt #" + i + " of " + flakyComponentRetries;
        LOG.trace(message, e);
        exception = new RuntimeException(message, e);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e1) {
        }
      }
    }
    throw exception;
  }
}
