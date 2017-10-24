package com.zero.selenium.ghost.page;

import org.openqa.selenium.WebDriver;
import com.zero.selenium.core.page.IAdminLogin;
import com.zero.selenium.core.page.PageUtils;

public class GhostPageUtilities {

  public static IAdminLogin openLogin(WebDriver driver) {
    PageUtils.maximize(driver);
    return PageUtils.openPage(driver, LoginPage.URL_FRAGMENT, LoginPage.class);
  }

  public static IGhostAdmin openAdmin(WebDriver driver) {
    PageUtils.maximize(driver);
    return PageUtils.openPage(driver, IGhostAdmin.URL_FRAGMENT, AdminPage.class);
  }

  public static IBlogPage openBlog(WebDriver driver) {
    PageUtils.maximize(driver);
    return PageUtils.openPage(driver, IBlogPage.URL_FRAGMENT, BlogPage.class);
  }

}
