package ldh.test;

//import ldh.common.testui.TestUIMainApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(value={"ldh.common.spring.security","ldh.test"})
public class TestServiceTest {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationBoot.class, args);
//        TestUIMainApp.start(args, applicationContext);
    }
}
