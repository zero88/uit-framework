package com.zero.selenium.core.component;

import java.util.function.Function;

public interface IComponentType {

  String getTagName();

  String getType();

  String getCssClassRegex();

  Function<String, String> getXpath();

}
