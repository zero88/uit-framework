package com.zero.selenium.core.page;

/**
 * Define page object
 * 
 * @author sontt
 *
 */
public interface IPage {

  String getRoot();

  String getUrlFragment();

  INavigation getNavigation();

}
