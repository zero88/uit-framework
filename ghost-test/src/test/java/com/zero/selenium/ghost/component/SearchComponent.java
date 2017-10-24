package com.zero.selenium.ghost.component;

import com.zero.selenium.core.component.Component;
import com.zero.selenium.core.component.data.IFormControl;
import com.zero.selenium.core.component.data.SingleData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchComponent extends Component implements IFormControl<SingleData> {

  private final String group;

  /**
   * Search without group
   */
  public SearchComponent() {
    this("");
  }

  @Override
  public String getRoot() {
    return this.getControlType().getXpath().apply("");
  }

  @SuppressWarnings("unchecked")
  @Override
  public SearchComponent internalSetValue(SingleData data) {
    new InputSearch().setValue(data);
    new DropDown(group).setValue(data);
    return this;
  }

  @Override
  public FormControlType getControlType() {
    return FormControlType.SEARCH_COMPONENT;
  }

}
