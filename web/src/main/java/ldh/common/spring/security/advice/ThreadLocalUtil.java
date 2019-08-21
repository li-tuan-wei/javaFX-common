package ldh.common.spring.security.advice;

/**
 * Created by ldh123 on 2018/10/14.
 */
public class ThreadLocalUtil {

    private static ThreadLocal<String> stringLocal = new ThreadLocal<String>();

    public static void setMerchantNo(String merchantNo) {
        stringLocal.set(merchantNo);
    }

    public static String getMerchantNo() {
        return stringLocal.get();
    }
}
