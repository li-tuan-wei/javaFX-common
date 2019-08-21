package ldh.test.config;

import ldh.common.spring.security.Merchantable;
import ldh.common.spring.security.service.EnumMerchantService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by ldh123 on 2018/10/14.
 */
@Component
public class SecurityConfig {

    @Bean
    public Merchantable merchantable() {
        return EnumMerchantService.Test002;
    }
}
