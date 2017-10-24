package com.zero.selenium.ghost.component;

import com.zero.selenium.core.action.SetText;
import com.zero.selenium.core.component.Component;
import com.zero.selenium.core.component.IInputField;
import com.zero.selenium.core.component.data.SingleData;

@SuppressWarnings("unchecked")
public class InputField extends Component implements IInputField {

  protected final String id;

  public InputField() {
    this("");
  }

  public InputField(String id) {
    this.id = id;
  }

  @Override
  public IInputField clear() {
    new SetText("").run(getRoot());
    return this;
  }

  @Override
  public String getValue() {
    return null;
  }

  @Override
  public InputField internalSetValue(SingleData data) {
    new SetText(data.getData()).run(getRoot());
    return this;
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.INPUT_TEXT;
  }

  @Override
  public String getRoot() {
    return getControlType().getXpath().apply(id);
  }

}
