package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.WebElement;

/**
 * {@link IAction} is core Selenium action interacts with element by Xpath.. <br/>
 * In which, XPath represents for one HTML element
 */
public interface IAction {

  void run();

  WebElement run(String xpath);

  /**
   * Run with many different xpath
   * 
   * @param xpaths
   * @return
   */
  List<WebElement> run(String... xpaths);

}
