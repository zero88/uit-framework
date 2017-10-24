package com.zero.selenium.ghost.component;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class EmailField extends InputField {

  public EmailField(String id) {
    super(id);
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.INPUT_EMAIL;
  }

}
