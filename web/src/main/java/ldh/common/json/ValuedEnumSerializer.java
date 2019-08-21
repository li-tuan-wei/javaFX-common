package ldh.common.json;

import com.google.gson.*;
import ldh.common.mybatis.ValuedEnum;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ldh on 2017/4/20.
 */
public class ValuedEnumSerializer implements JsonSerializer<ValuedEnum>, JsonDeserializer<ValuedEnum> {

    @Override
    public JsonElement serialize(ValuedEnum state, Type arg1,
                                 JsonSerializationContext arg2) {
        if (state.getValue() instanceof Number) {
            return new JsonPrimitive((Number)state.getValue());
        } else if (state.getValue() instanceof String) {
            return new JsonPrimitive((String)state.getValue());
        } else if (state.getValue() instanceof Boolean) {
            return new JsonPrimitive((Boolean)state.getValue());
        } else {
            throw new RuntimeException("error!!!!!!!");
        }

    }

    // json转为对象时调用,实现JsonDeserializer<PackageState>接口
    @Override
    public ValuedEnum deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) return null;
        try {
            Class clazz = Class.forName(typeOfT.getTypeName());
            ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];//获取当前new对象的泛型的父类类型
            Class param = (Class) parameterizedType.getActualTypeArguments()[0];
            Method method = clazz.getDeclaredMethod("getByValue", new Class[]{param});
            String value = json.getAsString();
            Method pmethod = param.getDeclaredMethod("valueOf", new Class[]{String.class});
            Object obj = pmethod.invoke(null, value);
            return (ValuedEnum) method.invoke(null, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
