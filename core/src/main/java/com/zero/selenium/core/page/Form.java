package com.zero.selenium.core.page;

import java.util.List;
import javax.inject.Inject;
import com.zero.selenium.DriverBase;
import com.zero.selenium.core.component.IFormControlFactory;
import com.zero.selenium.core.component.data.IControlData;
import com.zero.selenium.core.component.data.IFormControl;

public abstract class Form {
  
  @Inject
  private IFormControlFactory factory;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public void insertData(List<IControlData> data) {
    data.forEach(formData -> {
      String xpath = this.getLabelXpath(formData.getLabel());
      IFormControl<IControlData> control = (IFormControl<IControlData>) factory.parse(DriverBase.getDriver(), xpath);
      control.setValue(formData);
    });
  }

  public abstract String getLabelXpath(String label);

}
