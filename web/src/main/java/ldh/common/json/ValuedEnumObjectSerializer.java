package ldh.common.json;

import com.google.gson.*;
import ldh.common.mybatis.ValuedEnum;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ldh on 2017/4/20.
 */
public class ValuedEnumObjectSerializer implements JsonSerializer<ValuedEnum>, JsonDeserializer<ValuedEnum> {

    @Override
    public JsonElement serialize(ValuedEnum state, Type arg1,
                                 JsonSerializationContext arg2) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", state.toString());
        jsonObject.addProperty("desc", state.getDesc());
        return jsonObject;
    }

    // json转为对象时调用,实现JsonDeserializer<PackageState>接口
    @Override
    public ValuedEnum deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("value").getAsString();
            Class clazz = Class.forName(typeOfT.getTypeName());
            Method method = clazz.getDeclaredMethod("valueOf", new Class[]{String.class});
            Object obj = method.invoke(null, name);
            return (ValuedEnum) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
