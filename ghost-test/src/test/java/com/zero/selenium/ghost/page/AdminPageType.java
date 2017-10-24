package com.zero.selenium.ghost.page;

import com.zero.selenium.core.page.IAdminPage;
import com.zero.selenium.core.page.IPageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AdminPageType implements IPageType {

  USER("", "Users", UserPage.class),
  NEW_POST("Content", PostPage.class),
  POST("", "Posts", PostPage.class),
  TAG("Tags", "Tags", TagPage.class),
  USERS("Team", UsersPage.class),
  POSTS("Content", PostsPage.class);

  private final String pageMenu;
  private String searchGroup;
  private final Class<? extends IAdminPage> pageClass;

}
