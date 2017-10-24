package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.WebElement;

/**
 * {@link IActionList} is core Selenium action interacts with element by Xpath. <br/>
 * In which, XPath represents for list HTML element result
 */
public interface IActionList {

  List<WebElement> multipleRun(String xpath);

}
