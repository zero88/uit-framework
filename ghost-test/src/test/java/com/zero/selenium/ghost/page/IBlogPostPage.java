package com.zero.selenium.ghost.page;

public interface IBlogPostPage extends IBlogPage {

  @Override
  default IBlogPostPage goToPost(String title) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IBlogPostPage next();

  @Override
  public IBlogPostPage prev();

  public void share(SocialNetwork social);

  public void subscribe(String email);

}
