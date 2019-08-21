package ldh.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ldh on 2017/4/18.
 */
public class ParameterizedTypeUtil {

    public static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
