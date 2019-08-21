package ldh.common.spring.security.service.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ldh123 on 2018/10/13.
 */
public class BaseVo {

    private transient String merchantNo;
    private String data;
    private String transferDate;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public void setTransferDate(Date transferDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.transferDate = formatter.format(transferDate);

    }
}
