package ldh.common.spring.client;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.security.MerchantInfo;
import ldh.common.spring.security.Merchantable;
import ldh.common.spring.security.service.SecurityService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InterfaceFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private Class<T> mapperInterface;
    private String serverUrl;
    private ApplicationContext applicationContext;

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String path = UrlUtil.parsePath(mapperInterface, method);
                UrlInfo urlInfo = ClientUrlMapFactory.getInstance().getUrlInfo(path);

                Map<String, Object> dataMap = null;
                if (args.length > 0) {
                    dataMap = JsonUtil.toMap(method, args);
                }

                try {
                    String returnData = null;
                    if (!urlInfo.getMerchantable().equals("")) {
                        String merchantNo = dataMap.get("merchantNo").toString();
                        Merchantable merchantable = (Merchantable) applicationContext.getBean(urlInfo.getMerchantable());
                        MerchantInfo merchantInfo = merchantable.getMerchantInfo(merchantNo);
                        SecurityService securityService = (SecurityService) applicationContext.getBean(merchantInfo.getValidateService());
                        Object data = securityService.encode(dataMap, merchantable.getMerchantInfo(merchantNo).getInitData());
                        String json = HttpUtil.body(serverUrl + urlInfo.getPath(), JsonViewFactory.create().toJson(data));
                        isErrorThrow(json);
                        returnData = securityService.validate(json, merchantable.getMerchantInfo(merchantNo).getInitData());
                    } else {
                        returnData = HttpUtil.post(serverUrl + urlInfo.getPath(), dataMap);
                        isErrorThrow(returnData);
                    }

                    if (method.getReturnType() == Void.class) {
                        return null;
                    }

                    return JsonViewFactory.create().fromJson(returnData, method.getReturnType());
                } catch(Exception e) {
                    e.printStackTrace();
                }

                throw new RuntimeException("返还结果错误");
            }
        });
    }

    private void isErrorThrow(String returnData) {
        if (returnData != null) {
            if (returnData.startsWith("exception:")) {
                throw new RuntimeException(returnData.substring("exception:".length()));
            }
        }
    }

    public Class<T> getObjectType() {
        return this.mapperInterface;
    }

    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
