package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ldh123 on 2018/10/14.
 */
public abstract class DefaultSecurityService<T, P> implements SecurityService<T> {

    public P parse(String initData) {
        System.out.println(this.getClass().getGenericSuperclass());
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
//        Type objectType = new TypeToken<P>() {}.getType();
        return JsonViewFactory.create().fromJson(initData, types[1]);
    }
}
