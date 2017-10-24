package com.zero.selenium.core.component;

import java.util.function.Function;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import com.zero.selenium.core.action.XpathHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum FormControlType implements IComponentType {

  INPUT_TEXT("input", "gh-input", "text", generateByType("input", "text")),
  INPUT_EMAIL("input", "email*gh-input", "email", generateByType("input", "email")),
  INPUT_PASSWORD("input", "password*gh-input", "password", generateByType("input", "password")),
  INPUT_FILE("input", "x-file--input", "file", generateByType("input", "search")),
  INPUT_SEARCH("input", "", "file", generateByType("input", "file")),
  TEXT_AREA(
      "textarea",
      "ember-view"/* "ember-text-area|gh-input" */,
      generateByClass("textarea", "ember-view")),
  BUTTON("*", "btn", generateByClassOrType("*", "button", "btn")),
  BUTTON_SEARCH(
      "button",
      "gh-nav-search-button",
      generateByClass("button", "gh-nav-search-button")),
  BUTTON_DROPDOWN("button", "dropdown-toggle", generateByClass("button", "dropdown-toggle")),
  BUTTON_SUBMIT("button", "btn", "submit", generateByType("button", "submit")),
  SEARCH_COMPONENT("div", "gh-nav-search-input", "", generateByClass("div", "gh-nav-search-input")),
  DROPDOWN(
      "div",
      "ember-basic-dropdown-content",
      generateByClass("div", "ember-basic-dropdown-content"));

  private final String tagName;
  private final String cssClassRegex;
  private String type;
  private final Function<String, String> xpath;

  public static FormControlType parse(String tagName, String type, String cssClass) {
    if (StringUtils.isBlank(tagName) || StringUtils.isBlank(cssClass)) {
      return null;
    }
    for (FormControlType controlType : FormControlType.values()) {
      if (!controlType.tagName.equals(tagName) && !controlType.tagName.equals("*")) {
        continue;
      }
      if (!StringUtils.isBlank(controlType.type) && !controlType.type.equals(type)) {
        continue;
      }
      if ((controlType.type.equals(type) && StringUtils.isBlank(controlType.cssClassRegex))
          || Pattern.compile(controlType.cssClassRegex).matcher(cssClass).find()) {
        return controlType;
      }
    }
    return null;
  }

  private static Function<String, String> generateByType(String tag, String type) {
    return id -> {
      return StringUtils.isBlank(id) ? XpathHelper.generateByType(tag, type)
          : XpathHelper.generateById(tag, id);
    };
  }

  private static Function<String, String> generateByClass(String tag, String cssClass) {
    return id -> {
      return StringUtils.isBlank(id) ? XpathHelper.generateByHasClass(tag, cssClass)
          : XpathHelper.generateById(tag, id);
    };
  }

  private static Function<String, String> generateByClassOrType(String tag, String type,
      String cssClass) {
    return id -> {
      return StringUtils.isBlank(id) ? XpathHelper.generateByTypeOrClass(tag, type, cssClass)
          : XpathHelper.generateById(tag, id);
    };
  }

}
