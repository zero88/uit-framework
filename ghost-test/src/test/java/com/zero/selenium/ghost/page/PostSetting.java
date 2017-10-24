package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.Click;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.ghost.component.InputFile;

class PostSetting implements IPostSetting {

  @Override
  public void uploadImage(String filePath) {
    new InputFile().setValue(new SingleData(filePath));
  }

  @Override
  public void setImageLink(String imageURL) {

  }

  @Override
  public void deleteImage() {

  }

  @Override
  public void setPostURL(String path) {

  }

  @Override
  public IMetadata getMetadata() {
    return new Metadata();
  }

  @Override
  public void exit() {
    new Click().run(IGhostAdmin.getNavigationXpath());
  }

}
