package com.zero.selenium.core.component.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestStepData {

  private String name;
  @SuppressWarnings("rawtypes")
  @JsonAdapter(value = FormDataAdapter.class)
  private List<IControlData> data;
  private List<String> expected;
  private String action;
  private TestStepState state;

  public TestStepData() {
    this.state = TestStepState.CONTINUE;
    this.expected = new ArrayList<>();
  }

}
