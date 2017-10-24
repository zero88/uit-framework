package com.zero.selenium.ghost.component;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class TextArea extends InputField {

  public TextArea(String id) {
    super(id);
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.TEXT_AREA;
  }

}
