package ldh.common.spring.client;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Puhui on 2016/4/29.
 */
public class UrlInfo {

    private Url url;
    private String beanName;
    private Class<?> className;
    private Method method;
    private String path;
    private Url classUrl;
    private List<String> paramNames = null;
    private String merchantable;

    public UrlInfo(Url url, String beanName, Method method) {
        this.url = url;
        this.beanName = beanName;
        this.method = method;
    }

    public UrlInfo(Url url, Class<?> className, Method method) {
        this.url = url;
        this.className = className;
        this.method = method;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Url getClassUrl() {
        return classUrl;
    }

    public void setClassUrl(Url classUrl) {
        this.classUrl = classUrl;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public String getMerchantable() {
        return merchantable;
    }

    public void setMerchantable(String merchantable) {
        this.merchantable = merchantable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof UrlInfo)) return false;
        final UrlInfo instance = (UrlInfo)obj;;
        return path.equals(instance.getPath());
    }

    @Override
    public int hashCode() {
        if (path == null) return 0;
        return path.hashCode();
    }

    private void initPath() {
        path = url.path();
    }
}
