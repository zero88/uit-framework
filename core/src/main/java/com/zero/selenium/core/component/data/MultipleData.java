package com.zero.selenium.core.component.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class MultipleData extends ControlData<List<String>> {

  private final List<String> data;

  public MultipleData(String label) {
    super(label);
    this.data = new ArrayList<>();
  }

  public MultipleData addData(String data) {
    this.data.add(data);
    return this;
  }

  public MultipleData addData(String... data) {
    return addData(Arrays.asList(data));
  }

  public MultipleData addData(List<String> data) {
    this.data.addAll(data);
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (data == null ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    MultipleData other = (MultipleData) obj;
    if (data == null && other.data != null) {
      return false;
    }
    return data.equals(other.data);
  }

}
