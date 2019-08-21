package ldh.common.spring.client;

import java.lang.annotation.*;

/**
 * Created by ldh123 on 2018/10/19.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    String name();
}
