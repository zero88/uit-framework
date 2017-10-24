package com.zero.selenium.core.component.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class MapData extends ControlData<List<IControlData>> {

  private final List<IControlData> data;

  public MapData(String label) {
    super(label);
    this.data = new ArrayList<>();
  }

  @Override
  public List<IControlData> getData() {
    return data;
  }

  public MapData addData(IControlData formData) {
    this.data.add(formData);
    return this;
  }

  public MapData addData(IControlData... data) {
    return addData(Arrays.asList(data));
  }

  public MapData addData(List<IControlData> data) {
    this.data.addAll(data);
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (data == null ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    MapData other = (MapData) obj;
    if (data == null && other.data != null) {
      return false;
    }
    return data.equals(other.data);
  }

}
