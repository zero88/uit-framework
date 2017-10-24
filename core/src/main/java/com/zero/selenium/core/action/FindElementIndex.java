package com.zero.selenium.core.action;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import com.zero.selenium.DriverBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindElementIndex extends UIAction {

  private final String label;
  @Getter
  private int index;

  @Override
  protected WebElement doRun(String xpath) {
    List<WebElement> elements = DriverBase.getDriver().findElements(By.xpath(xpath));
    for (int i = 0; i < elements.size(); i++) {
      WebElement element = elements.get(i);
      try {
        element.findElement(By.xpath(XpathHelper.buildChildElementEqualsText(".", label)));
        this.index = i + 1;
        return element;
      } catch (NoSuchElementException e) {
        continue;
      }
    }
    throw new NoSuchElementException(
        "Unable to find index of element: {\"method\":\"xpath\", \"label\":\"" + label
            + "\", \"selector\":\"" + xpath + "\"}");
  }

}
