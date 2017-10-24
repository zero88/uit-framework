package com.zero.selenium.core.page;

/**
 * Admin Page
 * 
 * @author sontt
 *
 */
public interface IAdminPage extends IPage {

  public IUserPage openMyProfile();

  public void signOut();

}
