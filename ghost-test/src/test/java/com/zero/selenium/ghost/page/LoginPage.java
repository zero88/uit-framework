package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.core.page.IAdminLogin;
import com.zero.selenium.core.page.IAdminPage;
import com.zero.selenium.core.page.INavigation;
import com.zero.selenium.core.page.Page;
import com.zero.selenium.ghost.component.EmailField;
import com.zero.selenium.ghost.component.PasswordField;
import com.zero.selenium.ghost.component.SubmitButton;

class LoginPage extends Page implements IAdminLogin {

  public static final String URL_FRAGMENT = "/ghost/signin/";

  LoginPage() {
    super(XpathHelper.generateByHasClass("body", "ghost-login"), URL_FRAGMENT);
  }

  @Override
  public IGhostAdmin login(String userName, String password) {
    new EmailField().setValue(new SingleData(userName));
    new PasswordField().setValue(new SingleData(password));
    new SubmitButton().setValue(new SingleData("Sign in"));
    return new AdminPage();
  }

  @Override
  public IAdminPage forgotPassword(String userName) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public INavigation getNavigation() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public IAdminPage signup(String userName, String password) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

}
