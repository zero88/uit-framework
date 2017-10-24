package com.zero.selenium.ghost.page;

/**
 * Content setting for post or tag
 * 
 * @author sontt
 *
 */
public interface IContentSetting {

  public void uploadImage(String filePath);

  public void setImageLink(String imageURL);

  public void deleteImage();

  public void setPostURL(String path);

  public IMetadata getMetadata();

}
