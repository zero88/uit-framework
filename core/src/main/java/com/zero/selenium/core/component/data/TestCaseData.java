package com.zero.selenium.core.component.data;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zero.selenium.config.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseData {

  private static final Type TEST_CASE_DATA_TYPE = new TypeToken<TestCaseData>() {}.getType();
  private static final Type TEST_DATA_TYPE = new TypeToken<List<TestCaseData>>() {}.getType();

  private String name;

  private List<TestStepData> steps;

  @Override
  public String toString() {
    return this.name + " - " + steps.size() + " steps";
  }

  public static TestCaseData parseTCData(Reader reader) {
    return new GsonBuilder().create().fromJson(reader, TEST_CASE_DATA_TYPE);
  }

  public static TestCaseData parseTCData(String json) {
    return new GsonBuilder().create().fromJson(json, TEST_CASE_DATA_TYPE);
  }

  public static TestCaseData parseTCDataFromResource(String fileName) {
    return parseTCData(Configuration.instance().getReader(fileName));
  }

  public static List<TestCaseData> parseTestData(Reader reader) {
    return new GsonBuilder().create().fromJson(reader, TEST_DATA_TYPE);
  }

  public static List<TestCaseData> parseTestData(String json) {
    return new GsonBuilder().create().fromJson(json, TEST_DATA_TYPE);
  }

  public static List<TestCaseData> parseTestDataFromResource(String fileName) {
    return parseTestData(Configuration.instance().getReader(fileName));
  }

}
