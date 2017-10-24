package com.zero.selenium.tests;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.zero.selenium.DriverBase;
import com.zero.selenium.config.Configuration;
import com.zero.selenium.core.component.data.IControlData;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.core.component.data.TestCaseData;
import com.zero.selenium.core.component.data.TestStepData;
import com.zero.selenium.core.component.data.TestStepState;
import com.zero.selenium.core.page.IAdminLogin;
import com.zero.selenium.ghost.page.AdminPageType;
import com.zero.selenium.ghost.page.GhostPageUtilities;
import com.zero.selenium.ghost.page.IGhostAdmin;
import com.zero.selenium.ghost.page.IPostPage;
import com.zero.selenium.ghost.page.IPostSetting;
import com.zero.selenium.ghost.page.IPostsPage;
import com.zero.selenium.testdata.AdminDataProvider;
import com.zero.selenium.utils.ReflectUtils;

@SuppressWarnings({"rawtypes"})
@Test(dataProviderClass = AdminDataProvider.class)
public class GhostTestsXo extends DriverBase {

  private IGhostAdmin adminPage;

  @BeforeMethod(alwaysRun = true)
  public void login() {
    IAdminLogin loginPage = GhostPageUtilities.openLogin(getDriver());
    adminPage = (IGhostAdmin) loginPage.login(Configuration.instance().getTestUser(),
        Configuration.instance().getTestPassword());
    sleep();
  }

  @Test(groups = {SMOKE_GROUP, REGRESSION_GROUP}, dataProvider = "create-post")
  public void createPost(TestCaseData testCaseData) throws Exception {
    executeTest(testCaseData);
  }

  @Test(groups = {REGRESSION_GROUP}, dataProvider = "update-post")
  public void updatePost(TestCaseData testCaseData) {
    executeTest(testCaseData);
  }

  @Test(groups = {REGRESSION_GROUP}, dataProvider = "delete-post")
  public void deletePost(TestCaseData testCaseData) {
    executeTest(testCaseData);
  }

  protected void executeTest(TestCaseData testCaseData) {
    IPostsPage postsPage = adminPage.getNavigation().goTo(AdminPageType.POSTS);
    IPostPage newPostPage = postsPage.newPost();
    for (TestStepData stepData : testCaseData.getSteps()) {
      log().info("Step: " + stepData.getName());
      List<IControlData> data = stepData.getData();
      ReflectUtils.executeMethod(this, stepData.getAction(),
          new Class[] {IPostPage.class, List.class}, new Object[] {newPostPage, data});
      if (stepData.getState() == TestStepState.STOP) {
        break;
      }
    }
  }

  protected void inputPost(IPostPage newPostPage, List<IControlData> data) {
    Map<String, IControlData> mapData =
        data.stream().filter(d -> StringUtils.isNotBlank((String) d.getData()))
            .collect(Collectors.toMap(IControlData::getLabel, Function.identity()));
    SingleData title = (SingleData) mapData.get("title");
    SingleData content = (SingleData) mapData.get("content");
    newPostPage.inputTitle(title == null ? new SingleData(randomName()) : title);
    newPostPage.inputContent(content == null ? new SingleData(randomName(100)) : content);
    sleep();
    newPostPage.save();
  }

  // TODO enhancement with form.insertData() by label
  protected void updatePostSettings(IPostPage newPostPage, List<IControlData> data)
      throws URISyntaxException {
    IPostSetting setting = newPostPage.openSetting();
    setting.uploadImage(Configuration.instance().getFilePath("testdata/workflow.png").toString());
    // setting.getForm().insertData(data);
    setting.exit();
  }

  protected void publishPost(IPostPage newPostPage, List<IControlData> data) {
    sleep();
    newPostPage.publish();
  }

  protected void searchAndUpdatePost(IPostPage postPage, List<IControlData> data) {
    Map<String, IControlData> mapData =
        data.stream().filter(d -> StringUtils.isNotBlank((String) d.getData()))
            .collect(Collectors.toMap(IControlData::getLabel, Function.identity()));
    IPostPage editPostPage =
        postPage.searchAndGo((SingleData) mapData.get("search"), AdminPageType.POST);
    SingleData title = (SingleData) mapData.get("title");
    SingleData content = (SingleData) mapData.get("content");
    editPostPage.inputTitle(title == null ? new SingleData(randomName()) : title);
    editPostPage.inputContent(content == null ? new SingleData(randomName(150)) : content);
    sleep();
    editPostPage.update();
  }

  protected void searchAndDeletePost(IPostPage postPage, List<IControlData> data) {
    Map<String, IControlData> mapData =
        data.stream().filter(d -> StringUtils.isNotBlank((String) d.getData()))
            .collect(Collectors.toMap(IControlData::getLabel, Function.identity()));
    IPostPage editPostPage =
        postPage.searchAndGo((SingleData) mapData.get("search"), AdminPageType.POST);
    editPostPage.unpublish();
    editPostPage.delete();
  }

}
