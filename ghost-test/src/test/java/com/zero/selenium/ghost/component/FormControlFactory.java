package com.zero.selenium.ghost.component;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.zero.selenium.core.action.WaitElement;
import com.zero.selenium.core.action.XpathHelper;
import com.zero.selenium.core.component.FormControlType;
import com.zero.selenium.core.component.IFormControlFactory;
import com.zero.selenium.core.component.data.IControlData;
import com.zero.selenium.core.component.data.IFormControl;
import com.zero.selenium.core.component.data.TestCaseData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class helps to parse element automatically by label. It mostly uses when using
 * {@link TestCaseData} json
 * 
 * @author sontt
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FormControlFactory implements IFormControlFactory {

  private static IFormControlFactory instance;

  public static synchronized void initialize() {
    if (instance != null) {
      throw new IllegalStateException("Config already initialized");
    }
    instance = new FormControlFactory();
  }

  public static IFormControlFactory instance() {
    if (instance == null) {
      initialize();
    }
    return instance;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public IFormControl<? extends IControlData> parse(WebDriver driver, String labelXpath) {
    WebElement source = new WaitElement().run(labelXpath);
    String id = source.getAttribute("for");
    Element element = new Element(new WaitElement().run(XpathHelper.generateById("*", id)));
    FormControlType controlType =
        FormControlType.parse(element.getTag(), element.getType(), element.getClassName());
    if (controlType == null) {
      throw new NotFoundException("Not found control element: [" + element + "]");
    }
    if (controlType == FormControlType.INPUT_PASSWORD) {
      return new PasswordField(id);
    }
    if (controlType == FormControlType.INPUT_TEXT) {
      return new InputField(id);
    }
    if (controlType == FormControlType.INPUT_EMAIL) {
      return new EmailField(id);
    }
    if (controlType == FormControlType.TEXT_AREA) {
      return new TextArea(id);
    }
    if (controlType == FormControlType.BUTTON) {
      return new Button();
    }
    if (controlType == FormControlType.BUTTON_SUBMIT) {
      return new SubmitButton();
    }
    throw new UnsupportedOperationException("Not support form control element: [" + element + "]");
  }

  @Getter
  private static class Element {

    private final WebElement source;
    private String tag;
    private String id;
    private String className;
    private String type;

    Element(WebElement source) {
      this.source = source;
      tag = source.getTagName();
      id = source.getAttribute("id");
      type = source.getAttribute("type");
      className = source.getAttribute("class");
    }

    @Override
    public String toString() {
      return new StringBuilder().append("Tag: ").append(tag).append(" - ").append("Id: ").append(id)
          .append(" - ").append("CSS Class: ").append(className).append(" - ").append("Type:")
          .append(type).toString();
    }

  }
}
