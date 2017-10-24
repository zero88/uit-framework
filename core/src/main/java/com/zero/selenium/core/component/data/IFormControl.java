package com.zero.selenium.core.component.data;

import com.zero.selenium.config.LoggerFactory;
import com.zero.selenium.core.component.IComponentType;

@SuppressWarnings("rawtypes")
public interface IFormControl<T extends IControlData> {

  default <C extends IFormControl> C setValue(T data) {
    LoggerFactory.instance().getFormLog()
        .debug("Control: " + this.getControlType().toString() + " | Data: " + data);
    return internalSetValue(data);
  }

  <C extends IFormControl> C internalSetValue(T data);

  IComponentType getControlType();

}
