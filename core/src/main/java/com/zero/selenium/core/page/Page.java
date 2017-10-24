package com.zero.selenium.core.page;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import com.zero.selenium.config.LoggerFactory;
import com.zero.selenium.core.action.WaitElement;
import lombok.Getter;

@Getter
public abstract class Page implements IPage {

  protected final String root;
  protected final String urlFragment;

  protected Page(String root, String urlFragment) {
    this.root = root;
    this.urlFragment = urlFragment;
    try {
      new WaitElement().run(this.root);
    } catch (TimeoutException e) {
      throw new IllegalStateException("Couldn't identify " + this.getClass().getSimpleName(), e);
    }
  }

  protected Logger log() {
    return LoggerFactory.instance().getPageLog();
  }
}
