package com.zero.selenium.core.page;

public interface IPageType {

  public String getPageMenu();

  public <P extends IPage> Class<P> getPageClass();

  default boolean isAdminPage() {
    return IAdminPage.class.isInstance(this);
  }

  default boolean isPublicPage() {
    return IPublicPage.class.isInstance(this);
  }

}
