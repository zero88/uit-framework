package com.zero.selenium.testdata;

import java.util.List;
import org.testng.annotations.DataProvider;
import com.zero.selenium.core.component.data.TestCaseData;

public class AdminDataProvider {

  @DataProvider(name = "create-post")
  public static Object[] getCreatePosts() {
    List<TestCaseData> dataResources =
        TestCaseData.parseTestDataFromResource("testdata/create-post.json");
    return dataResources.toArray(new TestCaseData[dataResources.size()]);
  }

  @DataProvider(name = "update-post")
  public static Object[] getUpdatePosts() {
    List<TestCaseData> dataResources =
        TestCaseData.parseTestDataFromResource("testdata/update-post.json");
    return dataResources.toArray(new TestCaseData[dataResources.size()]);
  }

  @DataProvider(name = "delete-post")
  public static Object[] getDeletePosts() {
    List<TestCaseData> dataResources =
        TestCaseData.parseTestDataFromResource("testdata/delete-post.json");
    return dataResources.toArray(new TestCaseData[dataResources.size()]);
  }
}
