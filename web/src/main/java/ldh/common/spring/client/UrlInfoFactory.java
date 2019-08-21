package ldh.common.spring.client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Puhui on 2016/4/29.
 */
public class UrlInfoFactory {

    private static UrlInfoFactory instance = null;
    private ConcurrentMap<String, UrlInfo> pathUrlInfoMap = new ConcurrentHashMap();
    private Set<Class<?>> interfaceClassSet = new HashSet<Class<?>>();

    public static UrlInfoFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (UrlInfoFactory.class) {
            if (instance == null) {
                instance = new UrlInfoFactory();
            }
        }
        return instance;
    }

    private UrlInfoFactory() {}

    public void addInterfaceClass(Class<?> interfaceClass) {
        if (interfaceClassSet.contains(interfaceClass)) {
            throw new RuntimeException("class已经被解析：" + interfaceClass);
        }
        Url classPath = interfaceClass.getAnnotation(Url.class);
        if (classPath == null) return;
        Set<UrlInfo> methodUrlInfo = UrlUtil.parse(interfaceClass);
        for (UrlInfo urlInfo : methodUrlInfo) {
            String path = UrlUtil.parsePath(classPath, urlInfo.getUrl());
            if (pathUrlInfoMap.containsKey(path)) {
                throw new RuntimeException(path + "已经被定义");
            }
            pathUrlInfoMap.put(path, urlInfo);
        }
    }

    public UrlInfo getUrlInfo(String path) {
        UrlInfo urlInfo = pathUrlInfoMap.get(path);
        if (urlInfo == null) {
            throw new RuntimeException("路径未定义：" + path);
        }
        return urlInfo;
    }

    public boolean isInit() {
        return pathUrlInfoMap.size() > 0;
    }
}
