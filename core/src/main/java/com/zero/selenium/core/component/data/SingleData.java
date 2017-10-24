package com.zero.selenium.core.component.data;

import lombok.Getter;

@Getter
public class SingleData extends ControlData<String> {

  private final String data;

  /**
   * Constructor for {@link ARAButton} data.
   * 
   * @param data
   */
  public SingleData(String data) {
    super(data);
    this.data = data;
  }

  public SingleData(String label, String data) {
    super(label);
    this.data = data;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    return prime * super.hashCode() + (data == null ? 0 : data.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    SingleData other = (SingleData) obj;
    if (data == null && other.data != null) {
      return false;
    }
    return data.equals(other.data);
  }

}
