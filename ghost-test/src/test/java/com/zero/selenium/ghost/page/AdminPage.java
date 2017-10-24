package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.core.page.IUserPage;
import com.zero.selenium.core.page.Page;
import com.zero.selenium.core.page.PageTypeInvalid;
import com.zero.selenium.ghost.component.SearchComponent;
import com.zero.selenium.utils.ReflectUtils;

class AdminPage extends Page implements IGhostAdmin {

  protected AdminPage() {
    super(XpathHelper.generateByHasClass("body", "ember-application"), IGhostAdmin.URL_FRAGMENT);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P extends IGhostAdmin> P searchAndGo(SingleData valueToSearch, AdminPageType pageType) {
    if (pageType.isAdminPage()) {
      throw new PageTypeInvalid(pageType + " doesn't belong to admin");
    }
    new SearchComponent(pageType.getSearchGroup()).setValue(valueToSearch);
    return ReflectUtils.createInstance((Class<P>) pageType.getPageClass());
  }

  @Override
  public void signOut() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public IUserPage openMyProfile() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

}
