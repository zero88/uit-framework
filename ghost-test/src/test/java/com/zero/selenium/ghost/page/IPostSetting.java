package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.page.Form;
import com.zero.selenium.core.page.IHasForm;

public interface IPostSetting extends IContentSetting, IHasForm {

  public void exit();

  @Override
  default Form getForm() {
    return new Form() {
      @Override
      public String getLabelXpath(String label) {
        return XpathHelper.build(XpathHelper.generateByClass("div", "form-group"),
            XpathHelper.buildElementEqualsText("//label", label));
      }

    };
  }

}
