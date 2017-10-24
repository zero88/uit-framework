package com.zero.selenium.core.page;

import com.zero.selenium.core.component.data.SingleData;

public interface IDialog extends IPage {

  void close();

  void clickDialogButton(SingleData button);

}
