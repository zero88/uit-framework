package com.zero.selenium.core.component.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

@SuppressWarnings("rawtypes")
class FormDataAdapter extends TypeAdapter<List<IControlData>> {

  @Override
  public List<IControlData> read(JsonReader reader) throws IOException {
    List<IControlData> formData = new ArrayList<>();
    JsonToken token = reader.peek();
    if (JsonToken.BEGIN_OBJECT.equals(token)) {
      reader.beginObject();
      while (!JsonToken.END_OBJECT.equals(reader.peek())) {
        String label = reader.nextName();
        formData.add(processEachAttribute(reader, label));
      }
      reader.endObject();
    }
    return formData;
  }

  private IControlData processEachAttribute(JsonReader reader, String label) throws IOException {
    IControlData controlData;
    JsonToken token = reader.peek();
    if (JsonToken.BEGIN_OBJECT.equals(token)) {
      reader.beginObject();
      controlData = new MapData(label);
      while (!JsonToken.END_OBJECT.equals(reader.peek())) {
        String name = reader.nextName();
        ((MapData) controlData).addData(processEachAttribute(reader, name));
      }
      reader.endObject();
    } else if (JsonToken.BEGIN_ARRAY.equals(token)) {
      controlData = new MultipleData(label);
      reader.beginArray();
      while (!JsonToken.END_ARRAY.equals(reader.peek())) {
        ((MultipleData) controlData).addData(reader.nextString());
      }
      reader.endArray();
    } else if (JsonToken.BOOLEAN.equals(token)) {
      controlData = new SingleData(label, String.valueOf(reader.nextBoolean()));
    } else if (JsonToken.NULL.equals(token)) {
      controlData = new VoidData();
    } else {
      controlData = new SingleData(label, reader.nextString());
    }
    return controlData;
  }

  @Override
  public void write(JsonWriter out, List<IControlData> value) throws IOException {

  }

}
