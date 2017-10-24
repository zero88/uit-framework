package com.zero.selenium.ghost.page;

import org.apache.commons.lang3.StringUtils;
import com.zero.selenium.core.action.Click;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.core.page.IAdminPage;
import com.zero.selenium.core.page.INavigation;
import com.zero.selenium.core.page.IPageType;
import com.zero.selenium.core.page.IPublicPage;
import com.zero.selenium.core.page.PageTypeInvalid;
import com.zero.selenium.utils.ReflectUtils;

/**
 * Ghost Admin Page
 * 
 * @author sontt
 *
 */
public interface IGhostAdmin extends IAdminPage {

  String URL_FRAGMENT = "/ghost";

  static String getNavigationXpath() {
    return XpathHelper.generateByHasClass("ul", "gh-nav-list");
  }

  public <P extends IGhostAdmin> P searchAndGo(SingleData valueToSearch, AdminPageType pageType);

  @Override
  default INavigation getNavigation() {
    return new INavigation() {

      @Override
      public <P extends IPublicPage> P goToPublic(IPageType pageType) {
        return null;
      }

      @Override
      public <P extends IPublicPage> P goToPublic(String menu, Class<P> publicPage) {
        return null;
      }

      @Override
      public <P extends IAdminPage> P goTo(IPageType pageType) {
        if (pageType.isAdminPage()) {
          throw new PageTypeInvalid(pageType + " doesn't belong to admin");
        }
        return goTo(pageType.getPageMenu(), pageType.getPageClass());
      }

      @Override
      public <P extends IAdminPage> P goTo(String menu, Class<P> adminPage) {
        if (StringUtils.isBlank(menu)) {
          throw new PageTypeInvalid("Menu is empty");
        }
        new Click()
            .run(XpathHelper.buildChildElementEqualsText(IGhostAdmin.getNavigationXpath(), menu));
        return ReflectUtils.createInstance(adminPage);
      }
    };
  }
}
