package com.zero.selenium.config;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Allow creating a regular lambda function as an expected condition.
 */
public class ExpectedLambda {

  private static final Logger LOGGER = Logger.getLogger(ExpectedLambda.class.getName());

  /**
   * Safely (ignores exceptions) evaluates the result of supplier.
   *
   * @param supplier The supplier for the expected condition.
   * @param <T> The return type.
   * @return The expected condition.
   */
  public static <T> ExpectedCondition<T> lambda(Supplier<T> supplier) {
    return webDriver -> {
      try {
        return supplier.get();
      } catch (Throwable e) {
        logTempError(e);
        return null;
      }
    };

  }

  private static void logTempError(Throwable e) {
    // Ignore any exception and return null indicating the element was not found
    LOGGER.log(Level.INFO,
        "Not able to evaluate supplier during ExpectedCondition: {}. only an error when wait expires",
        e);
  }

  /**
   * Safely evaluates the result of supplier and checks if the returned element is visible
   * (::isDisplayed).
   *
   * @param supplier The supplier for the expected condition.
   * @return The expected condition.
   */
  public static ExpectedCondition<WebElement> visible(Supplier<WebElement> supplier) {
    return webDriver -> {
      try {
        WebElement element = supplier.get();
        if (!element.isDisplayed()) {
          return null;
        }

        return element;
      } catch (Exception e) {
        logTempError(e);
        return null;
      }
    };
  }
}
