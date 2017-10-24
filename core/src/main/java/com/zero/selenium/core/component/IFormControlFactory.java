package com.zero.selenium.core.component;

import org.openqa.selenium.WebDriver;
import com.zero.selenium.core.component.data.IControlData;
import com.zero.selenium.core.component.data.IFormControl;

@SuppressWarnings("rawtypes")
public interface IFormControlFactory {

  public IFormControl<? extends IControlData> parse(WebDriver driver, String labelXpath);

}
