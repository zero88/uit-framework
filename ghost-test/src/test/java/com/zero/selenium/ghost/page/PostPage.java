package com.zero.selenium.ghost.page;

import com.zero.selenium.core.action.VerifyElement;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.data.SingleData;
import com.zero.selenium.ghost.component.Button;
import com.zero.selenium.ghost.component.DropDownButton;
import com.zero.selenium.ghost.component.InputField;
import com.zero.selenium.ghost.component.TextArea;

class PostPage extends AdminPage implements IPostPage {


  @Override
  public String getTitle() {
    return new InputField("entry-title").getValue();
  }

  @Override
  public void inputTitle(SingleData title) {
    new InputField("entry-title").setValue(title);
  }

  @Override
  public void inputContent(SingleData content) {
    new TextArea().setValue(content);
  }

  @Override
  public void save() {
    doAction("Save Draft");
  }

  @Override
  public void update() {
    doAction("Update Post");
  }

  @Override
  public void publish() {
    doAction("Publish Now");
  }

  @Override
  public void unpublish() {
    doAction("Unpublish");
  }

  @Override
  public void delete() {
    new DropDownButton().setValue(new SingleData("Delete Post"));
    new Dialog("Are you sure you want to delete this post?")
        .clickDialogButton(new SingleData("Delete"));
  }

  @Override
  public IPostSetting openSetting() {
    if (new VerifyElement()
        .validate(XpathHelper.generateByHasClass("div", "settings-menu-expanded"))) {
      new Button().setValue(new SingleData("Post Settings"));
    }
    return new PostSetting();
  }

  private void doAction(String action) {
    new DropDownButton().setValue(new SingleData(action));
    new Button().setValue(new SingleData(action));
  }

}
