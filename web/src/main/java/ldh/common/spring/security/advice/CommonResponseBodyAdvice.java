package ldh.common.spring.security.advice;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.security.MerchantInfo;
import ldh.common.spring.security.Merchantable;
import ldh.common.spring.security.SecurityBody;
import ldh.common.spring.security.service.SecurityService;
import ldh.common.spring.security.service.vo.BaseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * Created by ldh123 on 2018/10/13.
 */
@ControllerAdvice
public class CommonResponseBodyAdvice implements ResponseBodyAdvice, ApplicationContextAware {

    private Logger LOGGER = LoggerFactory.getLogger(CommonResponseBodyAdvice.class);

    private ApplicationContext applicationContext;

    @Resource
    private Merchantable merchantable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        SecurityBody securityBody = methodParameter.getMethod().getAnnotation(SecurityBody.class);
        return securityBody != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String merchantNo = ThreadLocalUtil.getMerchantNo();
        MerchantInfo merchantInfo = merchantable.getMerchantInfo(merchantNo);
        SecurityService securityService = applicationContext.getBean(merchantInfo.getValidateService(), SecurityService.class);

//        String srcData = JsonViewFactory.create().toJson(o);
        Object encodeData = securityService.encode(o, merchantInfo.getInitData());
        if (encodeData instanceof BaseVo) {
            BaseVo baseVo = (BaseVo) encodeData;
            baseVo.setMerchantNo(merchantNo);
        }
        if (o instanceof String) {
            return JsonViewFactory.create().toJson(encodeData);
        }
        return encodeData;
    }
}
