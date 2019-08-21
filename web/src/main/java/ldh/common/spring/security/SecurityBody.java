package ldh.common.spring.security;

import java.lang.annotation.*;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface SecurityBody {
}
