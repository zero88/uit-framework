package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.page.INavigation;
import com.zero.selenium.core.page.Page;

class BlogPage extends Page implements IBlogPage {

  BlogPage() {
    super(XpathHelper.generateByHasClass("body", "home-template"), "");
  }

  @Override
  public IBlogPage goToBlog() {
    return null;
  }

  @Override
  public IBlogPostPage goToPost(String title) {
    return null;
  }

  @Override
  public IBlogPage goByAuthor(String author) {
    return null;
  }

  @Override
  public IBlogPage goByTag(String tag) {
    return null;
  }

  @Override
  public IBlogPage next() {
    return null;
  }

  @Override
  public IBlogPage prev() {
    return null;
  }

  @Override
  public INavigation getNavigation() {
    return null;
  }
}
