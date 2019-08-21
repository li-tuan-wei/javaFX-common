package ldh.common.spring.client;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Puhui on 2016/4/29.
 */
public class UrlUtil {

    public static Set<UrlInfo> parse(Class<?> interfaceClass) {
        Set<UrlInfo> result = new HashSet<UrlInfo>();
        Url pUrl = interfaceClass.getAnnotation(Url.class);
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            Url url = method.getAnnotation(Url.class);
            if (url != null) {
                UrlInfo urlInfo = new UrlInfo(url, interfaceClass, method);
                urlInfo.setClassUrl(pUrl);
                urlInfo.setPath(parsePath(interfaceClass, method));
                setFilter(urlInfo, pUrl);
                setFilter(urlInfo, url);
                result.add(urlInfo);
            }
        }
        Class<?> superClass = interfaceClass.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            Set<UrlInfo> childrenSet = parse(superClass);
            result.addAll(childrenSet);
        }

        return result;
    }

    public static boolean isUrlAnnotation(Method method) {
        Url url = method.getAnnotation(Url.class);
        if (url == null) {
            return false;
        }
        return true;
    }

    public static String parsePath(Class<?> interfaceClass, Method method) {
        Url pUrl = interfaceClass.getAnnotation(Url.class);
        Url url = method.getAnnotation(Url.class);
        String path = parsePath(pUrl, url);
        if (path == null)
            throw new RuntimeException("无法解析出路径：" + interfaceClass + ", method:" + method);
        return path;
    }

    public static String parsePath(Url classUrl, Url methodUrl) {
        if (methodUrl != null) {
            String path = methodUrl.path();
            if (classUrl != null) {
                path =  classUrl.path() + path;
            }
            return path;
        }
        return null;
    }

    private static void setFilter(UrlInfo urlInfo, Url url) {
        if (url.merchantable() != null && !url.merchantable().equals("")) {
            urlInfo.setMerchantable(url.merchantable());
        }
    }

}

