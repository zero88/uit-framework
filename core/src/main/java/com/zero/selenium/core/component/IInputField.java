package com.zero.selenium.core.component;

import com.zero.selenium.core.component.data.IFormControl;
import com.zero.selenium.core.component.data.SingleData;

public interface IInputField extends IFormControl<SingleData> {

  IInputField clear();

  String getValue();

}
