package com.zero.selenium.core.component.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
abstract class ControlData<T> implements IControlData<T> {

  private final String label;

  @Override
  public final String toString() {
    return "[label=" + this.getLabel() + " - data=" + this.getData() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (label == null ? 0 : label.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    @SuppressWarnings("rawtypes")
    ControlData other = (ControlData) obj;
    if (label == null && other.label != null) {
      return false;
    }
    return label.equals(other.label);
  }

}
