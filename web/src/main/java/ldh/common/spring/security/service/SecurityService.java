package ldh.common.spring.security.service;

/**
 * Created by ldh123 on 2018/10/13.
 */
public interface SecurityService<T> {

    String validate(String data, String initData) throws SecurityException;

    T encode(Object data, String initData) throws SecurityException;
}
