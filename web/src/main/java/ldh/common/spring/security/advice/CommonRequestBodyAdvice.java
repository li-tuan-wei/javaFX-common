package ldh.common.spring.security.advice;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.security.MerchantInfo;
import ldh.common.spring.security.Merchantable;
import ldh.common.spring.security.SecurityBody;
import ldh.common.spring.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by ldh123 on 2018/10/13.
 */
@ControllerAdvice
public class CommonRequestBodyAdvice extends RequestBodyAdviceAdapter implements ApplicationContextAware {

    private Logger LOGGER = LoggerFactory.getLogger(CommonRequestBodyAdvice.class);
    private ApplicationContext applicationContext;

    @Resource
    private Merchantable merchantable;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        SecurityBody securityBody = methodParameter.getMethod().getAnnotation(SecurityBody.class);
        return securityBody != null;
    }

    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        int size = inputMessage.getBody().available();
        byte[] bytes = new byte[size];
        inputMessage.getBody().read(bytes);
        String jsonData = new String(bytes, "utf-8");
        LOGGER.info("srcData:{}", jsonData);
        Map map = JsonViewFactory.create().fromJson(jsonData, Map.class);
        if (!map.containsKey("merchantNo")) {
            throw new RuntimeException("参数错误");
        }
        String merchantNo = map.get("merchantNo").toString();
        MerchantInfo merchantInfo = merchantable.getMerchantInfo(merchantNo);
        SecurityService validateService = applicationContext.getBean(merchantInfo.getValidateService(), SecurityService.class);
        String decodeData = validateService.validate(jsonData, merchantInfo.getInitData());
        ThreadLocalUtil.setMerchantNo(merchantNo);
        return new BytesInputMessage(new ByteArrayInputStream(decodeData.getBytes("utf-8")), inputMessage.getHeaders());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public class BytesInputMessage implements HttpInputMessage {
        private final InputStream body;
        private final HttpHeaders headers;

        public BytesInputMessage(InputStream body, HttpHeaders headers) {
            this.body = body;
            this.headers = headers;
        }

        public InputStream getBody() throws IOException {
            return this.body;
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

    }
}
