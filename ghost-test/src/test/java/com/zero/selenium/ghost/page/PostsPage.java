package com.zero.selenium.ghost.page;

import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.ghost.component.Button;

class PostsPage extends AdminPage implements IPostsPage {

  @Override
  public IPostPage newPost() {
    new Button().setValue(new SingleData("New Post"));
    return new PostPage();
  }

  @Override
  public IPostPage selectPost(String title) {
    return null;
  }

  @Override
  public IPostPage editPost(String title) {
    return null;
  }

}
