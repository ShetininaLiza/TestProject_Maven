package org.example.helper.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RecordAdapter  implements JsonSerializer<Record>, JsonDeserializer<Record> {
    @Override
    public JsonElement serialize(Record src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        System.out.println("serialize");

        result.add("id", new JsonPrimitive(src.getNum()));
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

    @Override
    public Record deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            var packege = Record.class.getPackage().getName();
            String path = packege+"." + type;
            Record data = context.deserialize(element, Class.forName(path));
            data.setNum(Integer.parseInt(id));
            return data;
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}
