package com.zero.selenium.ghost.component;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import com.zero.selenium.core.action.Click;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.Component;
import com.zero.selenium.core.component.IComponentType;
import com.zero.selenium.core.component.data.IFormControl;
import com.zero.selenium.core.component.data.SingleData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DropDown extends Component implements IFormControl<SingleData> {

  private final String group;

  /**
   * Search without group
   */
  public DropDown() {
    this("");
  }

  @Override
  public String getRoot() {
    return getControlType().getXpath().apply("");
  }

  @SuppressWarnings("unchecked")
  @Override
  public DropDown internalSetValue(SingleData data) {
    if (StringUtils.isBlank(group)) {
      click(getRoot(), data);
    } else {
      String root = XpathHelper.build(getRoot(), XpathHelper.buildElementHasChildEqualsText(
          XpathHelper.generateByClass("li", "ember-power-select-group"), group));
      click(root, data);
    }
    return this;
  }

  @Override
  public IComponentType getControlType() {
    return FormControlType.DROPDOWN;
  }

  private WebElement click(String root, SingleData data) {
    return new Click().run(XpathHelper.build(root, XpathHelper.buildChildElementEqualsText(
        XpathHelper.generateByHasClass("li", "ember-power-select-option"), data.getData())));
  }


}
