package ldh.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author ldh on 2018-10-06 11:28:06
 *
*/
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(value={"ldh.common","ldh.test"})
public class ApplicationBoot extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
