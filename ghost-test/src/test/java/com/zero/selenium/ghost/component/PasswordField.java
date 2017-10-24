package com.zero.selenium.ghost.component;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class PasswordField extends InputField {

  public PasswordField(String id) {
    super(id);
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.INPUT_PASSWORD;
  }

}
