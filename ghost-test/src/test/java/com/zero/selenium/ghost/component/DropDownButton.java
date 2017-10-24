package com.zero.selenium.ghost.component;

import org.openqa.selenium.WebElement;
import com.zero.selenium.core.action.Click;
import com.zero.selenium.core.action.WaitElement;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;

@SuppressWarnings("unchecked")
public class DropDownButton extends Button {

  private static final String OPEN_STATE = "open";
  private final String valueXpath;

  public DropDownButton() {
    this.valueXpath =
        XpathHelper.build(getRoot(), "/..", XpathHelper.generateByHasClass("div", "dropdown"));
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.BUTTON_DROPDOWN;
  }

  @Override
  public DropDownButton internalSetValue(SingleData data) {
    WebElement element = new WaitElement().run(getRoot());
    boolean isCurrentOpen = element.getAttribute("class").contains(OPEN_STATE);
    if (!isCurrentOpen) {
      new Click().run(getRoot());
    }
    String xpath = XpathHelper.build(valueXpath,
        XpathHelper.buildChildElementEqualsText("//li", data.getData()));
    new WaitElement().run(xpath);
    new Click().run(xpath);
    return this;
  }

}
