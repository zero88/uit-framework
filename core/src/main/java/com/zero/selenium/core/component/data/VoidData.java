package com.zero.selenium.core.component.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class VoidData implements IControlData<Void> {

  @Override
  public Void getData() {
    return null;
  }

  @Override
  public String getLabel() {
    return null;
  }

}
