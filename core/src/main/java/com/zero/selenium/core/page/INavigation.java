package com.zero.selenium.core.page;

/**
 * Navigation panel for webapp. It can be top/lef/right position.
 * 
 * @author sontt
 *
 */
public interface INavigation {

  <P extends IAdminPage> P goTo(String menu, Class<P> adminPage);

  <P extends IAdminPage> P goTo(IPageType pageType);

  <P extends IPublicPage> P goToPublic(String menu, Class<P> publicPage);

  <P extends IPublicPage> P goToPublic(IPageType pageType);

}
