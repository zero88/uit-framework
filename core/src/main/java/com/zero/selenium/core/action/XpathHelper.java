package com.zero.selenium.core.action;

public final class XpathHelper {

  public static String buildLinkWithTitle(String xpath, String value) {
    StringBuilder builder = new StringBuilder();
    normalizeAndXpath(xpath, builder);
    builder.append("(text()=").append(escapseQuote(value));
    builder.append(" or ");
    builder.append("@title=").append(escapseQuote(value));
    builder.append(")]");
    return builder.toString();
  }

  public static String buildChildElementContainsText(String parent, String value) {
    return parent + "//*[contains(text(), " + escapseQuote(value) + ")]";
  }

  public static String buildChildElementEqualsText(String parent, String value) {
    return parent + "//*[text()=" + escapseQuote(value) + "]";
  }

  /**
   * Get element contains text
   *
   * @param xpath
   * @param value
   * @return
   */
  public static String buildElementContainsText(String xpath, String value) {
    StringBuilder builder = new StringBuilder();
    normalizeAndXpath(xpath, builder);
    builder.append("contains(text(), ").append(escapseQuote(value)).append(")]");
    return builder.toString();
  }

  public static String buildElementEqualsText(String xpath, String value) {
    StringBuilder builder = new StringBuilder();
    normalizeAndXpath(xpath, builder);
    builder.append("./text()=").append(escapseQuote(value)).append("]");
    return builder.toString();
  }

  public static String buildElementHasChildEqualsText(String xpath, String value) {
    StringBuilder builder = new StringBuilder();
    normalizeAndXpath(xpath, builder);
    builder.append("*//text()=").append(escapseQuote(value)).append("]");
    return builder.toString();
  }

  public static String buildElementHasChildContainsText(String xpath, String value) {
    StringBuilder builder = new StringBuilder();
    normalizeAndXpath(xpath, builder);
    builder.append(".//*[contains(text(), ").append(escapseQuote(value)).append(")]]");
    return builder.toString();
  }

  public static String buildElementByIndex(String parent, String child, int index) {
    StringBuilder builder = new StringBuilder();
    builder.append(parent).append("/").append(child).append("[").append(index).append("]");
    return builder.toString();
  }

  public static String build(String parent, String... subs) {
    StringBuilder builder = new StringBuilder();
    builder.append(parent);
    for (String sub : subs) {
      builder.append(sub.matches("^(\\.|\\/).+") ? sub : "//" + sub);
    }
    return builder.toString();
  }


  public static String generateByHasClass(String tag, String cssClass) {
    StringBuilder builder = new StringBuilder();
    builder.append("//").append(tag);
    builder.append("[contains(concat(' ', normalize-space(@class), ' '), ' ").append(cssClass)
        .append(" ')]");
    return builder.toString();
  }

  public static String generateById(String tag, String id) {
    return "//" + tag + "[@id='" + id + "']";
  }

  public static String generateByTypeOrClass(String tag, String type, String cssClass) {
    StringBuilder builder = new StringBuilder();
    builder.append("//").append(tag);
    builder.append("[(contains(concat(' ', normalize-space(@class), ' '), ' ").append(cssClass)
        .append(" ')");
    builder.append(" or ").append("@type='").append(type).append("')]");
    return builder.toString();
  }

  public static String generateByClass(String tag, String cssClass) {
    return "//" + tag + "[@class='" + cssClass + "']";
  }

  public static String generateByType(String tag, String type) {
    return "//" + tag + "[@type='" + type + "']";
  }

  private static void normalizeAndXpath(String xpath, StringBuilder builder) {
    normailzeXpath(xpath, "and", builder);
  }

  private static void normalizeOrXpath(String xpath, StringBuilder builder) {
    normailzeXpath(xpath, "or", builder);
  }

  private static void normailzeXpath(String xpath, String operation, StringBuilder builder) {
    if (xpath.endsWith("]")) {
      builder.append(xpath.substring(0, xpath.lastIndexOf("]"))).append(" ").append(operation)
          .append(" ");
    } else {
      builder.append(xpath).append("[");
    }
  }

  private static String escapseQuote(String value) {
    return "concat('" + value.replace("'", "', \"'\", '") + "', '')";
  }
}
