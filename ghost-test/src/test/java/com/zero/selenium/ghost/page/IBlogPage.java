package com.zero.selenium.ghost.page;

import com.zero.selenium.core.page.IPublicPage;

/**
 * Public page
 * 
 * @author sontt
 *
 */
public interface IBlogPage extends IPublicPage {

  String URL_FRAGMENT = "";

  public IBlogPage goToBlog();

  public IBlogPostPage goToPost(String title);

  public IBlogPage goByAuthor(String author);

  public IBlogPage goByTag(String tag);

  public IBlogPage next();

  public IBlogPage prev();

}
