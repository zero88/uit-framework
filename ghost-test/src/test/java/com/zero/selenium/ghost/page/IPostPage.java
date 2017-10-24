package com.zero.selenium.ghost.page;

import com.zero.selenium.core.component.data.SingleData;

public interface IPostPage extends IGhostAdmin {

  public String getTitle();

  public void inputTitle(SingleData title);

  public void inputContent(SingleData content);

  public IPostSetting openSetting();

  public void save();

  public void update();

  public void publish();

  public void unpublish();

  public void delete();

}
