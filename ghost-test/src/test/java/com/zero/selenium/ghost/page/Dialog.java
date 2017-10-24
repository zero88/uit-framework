package com.zero.selenium.ghost.page;

import org.openqa.selenium.TimeoutException;
import com.zero.selenium.core.action.WaitElement;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.core.page.IDialog;
import com.zero.selenium.core.page.INavigation;
import com.zero.selenium.core.page.Page;
import com.zero.selenium.ghost.component.Button;

class Dialog extends Page implements IDialog {

  protected Dialog(String header) {
    super(XpathHelper.generateByHasClass("div", "fullscreen-modal"), "");
    try {
      new WaitElement().run(XpathHelper.buildChildElementEqualsText(
          XpathHelper.generateByHasClass("header", "modal-header"), header));
    } catch (TimeoutException e) {
      throw new IllegalStateException("Couldn't identify dialog with header: '" + header + "'", e);
    }
  }

  @Override
  public void close() {

  }

  @Override
  public void clickDialogButton(SingleData button) {
    new Button(XpathHelper.generateByHasClass("div", "modal-footer")).setValue(button);
  }

  @Override
  public INavigation getNavigation() {
    throw new UnsupportedOperationException("Not support");
  }

}
