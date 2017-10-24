package com.zero.selenium.core.page;

public interface ILoginPage<P extends IPage> extends IPage {

  P login(String userName, String password);

  P signup(String userName, String password);

  P forgotPassword(String userName);

}
