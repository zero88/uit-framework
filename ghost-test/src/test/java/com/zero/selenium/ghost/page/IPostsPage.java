package com.zero.selenium.ghost.page;

public interface IPostsPage extends IGhostAdmin {

  public IPostPage newPost();

  public IPostPage selectPost(String title);

  public IPostPage editPost(String title);
}
