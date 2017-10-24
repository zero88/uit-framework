package com.zero.selenium.core.page;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import com.zero.selenium.config.Configuration;
import com.zero.selenium.utils.ReflectUtils;

public class PageUtils {

  public static void maximize(WebDriver driver) {
    driver.manage().window().maximize();
  }

  public static void resize(WebDriver driver, int width, int height) {
    driver.manage().window().setPosition(new Point(0, 0));
    driver.manage().window().setSize(new Dimension(width, height));
  }

  public static <P extends IPage> P openPage(WebDriver driver, String urlFragment, Class<P> clazz) {
    maximize(driver);
    driver.get(Configuration.instance().getURL() + urlFragment);
    P instance = ReflectUtils.createInstance(clazz);
    return instance;
  }

}
