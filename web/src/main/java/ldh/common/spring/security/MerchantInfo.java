package ldh.common.spring.security;

/**
 * Created by ldh123 on 2018/10/13.
 */
public class MerchantInfo {

    private String initData;
    private String validateService;


    public MerchantInfo(String validateService, String initData) {
        this.validateService = validateService;
        this.initData = initData;
    }

    public String getInitData() {
        return initData;
    }

    public String getValidateService() {
        return validateService;
    }
}
