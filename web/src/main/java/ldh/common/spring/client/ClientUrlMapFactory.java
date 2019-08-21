package ldh.common.spring.client;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Puhui on 2016/4/29.
 */
public class ClientUrlMapFactory {

    private static ClientUrlMapFactory instance = null;
    private ConcurrentMap<String, UrlInfo> pathUrlInfoMap = new ConcurrentHashMap();
    private Set<Class<?>> interfaceClassSet = new CopyOnWriteArraySet<Class<?>>();

    public static ClientUrlMapFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (ClientUrlMapFactory.class) {
            if (instance == null) {
                instance = new ClientUrlMapFactory();
            }
        }
        return instance;
    }

    private ClientUrlMapFactory() {}

    public void addInterfaceClass(Class<?> interfaceClass) {
        if (interfaceClassSet.contains(interfaceClass)) {
            return;
//            throw new RuntimeException("class已经被解析：" + interfaceClass);
        }
        Url classPath = interfaceClass.getAnnotation(Url.class);
        Set<UrlInfo> methodUrlInfo = UrlUtil.parse(interfaceClass);
        for (UrlInfo urlInfo : methodUrlInfo) {
            String path = UrlUtil.parsePath(classPath, urlInfo.getUrl());
            if (pathUrlInfoMap.containsKey(path)) {
                throw new RuntimeException(path + "已经被定义");
            }
            pathUrlInfoMap.put(path, urlInfo);
        }
        interfaceClassSet.add(interfaceClass);
    }

    public UrlInfo getUrlInfo(String path) {
        UrlInfo urlInfo = pathUrlInfoMap.get(path);
        if (urlInfo == null) {
            throw new RuntimeException("路径未定义：" + path);
        }
        return urlInfo;
    }

}
