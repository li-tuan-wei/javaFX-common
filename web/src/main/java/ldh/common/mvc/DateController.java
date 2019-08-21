package ldh.common.mvc;

import ldh.common.spring.date.DateNowUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by ldh on 2018/10/8.
 */
@Controller
public class DateController {

    @Resource
    private Environment env;

    @RequestMapping(value = "/version/setTimeOffset")
    @ResponseBody
    public String setTimeOffset(@RequestParam("date") String date) throws ParseException {
        if (DateNowUtil.isProd(env)) {
            return "environment is prod, it can't set offsetSecond";
        }
        if (date == null || date.trim().equals("")) {
            Date nowDate = DateNowUtil.getNowDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(nowDate);
        }
        Date newNow = null;
        if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            newNow = simpleDateFormat.parse(date);
        }
        if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newNow = simpleDateFormat.parse(date);
        }
        if (newNow == null) {
            return "date is format error!";
        }
        long offsetSecond = System.currentTimeMillis() - newNow.getTime();
        DateNowUtil.setOffsetSecond(offsetSecond/1000);
        return "success";
    }
}
