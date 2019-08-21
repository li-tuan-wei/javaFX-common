package ldh.common.spring.client;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Puhui on 2016/4/29.
 */
public class JsonUtil {

    public static String json(Method method, Object[] args) {
        Map<String, Object> paramMap = toMap(method, args);
        return toJson(paramMap);
    }

    public static Map<String, Object> toMap(Method method, Object[] args) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Parameter[] params2 = method.getParameters();
        int i=0;
        for (Parameter param : params2) {
            paramMap.put(param.getName(), args[i++]);
        }
        return paramMap;
    }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
