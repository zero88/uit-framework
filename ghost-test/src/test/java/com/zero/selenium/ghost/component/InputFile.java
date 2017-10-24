package com.zero.selenium.ghost.component;

import com.zero.selenium.core.action.UploadFile;
import com.zero.selenium.core.component.IInputField;
import com.zero.selenium.core.component.data.SingleData;

public final class InputFile extends InputField {

  @Override
  public IInputField clear() {
    return super.clear();
  }

  @Override
  public String getValue() {
    return super.getValue();
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.INPUT_FILE;
  }

  @SuppressWarnings("unchecked")
  @Override
  public InputField internalSetValue(SingleData data) {
    new UploadFile(data.getData()).run(getRoot());
    return this;
  }

}
