package com.zero.selenium.ghost.component;

public class SubmitButton extends Button {

  @Override
  public FormControlType getControlType() {
    return FormControlType.BUTTON_SUBMIT;
  }

}
