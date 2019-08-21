package ldh.common.spring.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ldh on 2018/10/8.
 */
public class DateNowUtil {

    @Resource
    private static Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(DateNowUtil.class);

    // 时间偏移
    private static volatile long offsetSecond = 0;

    public static void setOffsetSecond(Long offsetSecond) {
        if (!isProd(env)) {
            DateNowUtil.offsetSecond = offsetSecond;
        } else {
            LOGGER.warn("environment is prod, it can't set offsetSecond");
        }
    }

    public static Date getNowDate() {
        if (offsetSecond <= 0) {
            return new Date();
        }
        long time = System.currentTimeMillis() + offsetSecond * 1000L;
        return new Date(time);
    }

    public static boolean isProd(Environment env) {
        if (Arrays.stream(env.getActiveProfiles()).filter(profile->profile.toLowerCase().startsWith("prod")).count() > 0) {
            return true;
        }
        return false;
    }

}
