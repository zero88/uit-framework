package com.zero.selenium.core.component;

import org.apache.logging.log4j.Logger;
import com.zero.selenium.config.LoggerFactory;

public abstract class Component implements IComponent {

  protected Logger log() {
    return LoggerFactory.instance().getComponentLog();
  }

}

