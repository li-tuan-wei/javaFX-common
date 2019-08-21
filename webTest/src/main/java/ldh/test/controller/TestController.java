package ldh.test.controller;

import ldh.common.spring.security.SecurityBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldh123 on 2018/10/14.
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping(path="/hello")
    @SecurityBody
    public String hello(@RequestBody String tt) {
        return "hello world";
    }

    @ResponseBody
    @RequestMapping(path="/hello2")
    @SecurityBody
    public Map<String, Object> hello2(@RequestBody String tt) {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "hello world");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/demo")
    public String hello() {
        return "hello world";
    }
}
