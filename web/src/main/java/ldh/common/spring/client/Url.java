package ldh.common.spring.client;

import java.lang.annotation.*;

/**
 * Created by Puhui on 2016/4/29.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {

    String path();

    RequestType requestType() default RequestType.Body;

    String merchantable() default "";
}
