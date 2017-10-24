package com.zero.selenium.ghost.component;

import com.zero.selenium.core.action.Click;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.Component;
import com.zero.selenium.core.component.IComponentType;
import com.zero.selenium.core.component.data.IFormControl;
import com.zero.selenium.core.component.data.SingleData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class Button extends Component implements IFormControl<SingleData> {

  private final String parentXpath;

  public Button() {
    this("");
  }


  @Override
  public Button internalSetValue(SingleData data) {
    new Click().run(XpathHelper.buildLinkWithTitle(getRoot(), data.getData()));
    return this;
  }

  @Override
  public IComponentType getControlType() {
    return FormControlType.BUTTON;
  }

  @Override
  public final String getRoot() {
    return XpathHelper.build(parentXpath, this.getControlType().getXpath().apply(""));
  }

}
