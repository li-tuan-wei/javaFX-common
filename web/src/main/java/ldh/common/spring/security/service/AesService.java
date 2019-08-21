package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.date.DateNowUtil;
import ldh.common.spring.security.cryptogram.AesUtil;
import ldh.common.spring.security.service.vo.AesParam;
import ldh.common.spring.security.service.vo.AesVo;
import org.springframework.stereotype.Service;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Service
public class AesService extends DefaultSecurityService<AesVo, AesParam> {

    public String encode(String data, String password) {
        return AesUtil.encrypt(data, password);
    }

    public String decode(String encodeData, String password) {
        return AesUtil.decrypt(encodeData, password);
    }

    @Override
    public String validate(String data, String initData) {
        AesParam aesParam = parse(initData);
        AesVo aesVo = JsonViewFactory.create().fromJson(data, AesVo.class);
        String srcData = decode(aesVo.getData(), aesParam.getSecretKey());
        return srcData;
    }

    @Override
    public AesVo encode(Object data, String initData) throws SecurityException {
        AesParam aesParam = parse(initData);
        String srcData = data.toString();
        if (!(data instanceof String)) {
            srcData = JsonViewFactory.create().toJson(data);
        }
        String encodeData = encode(srcData, aesParam.getSecretKey());
        AesVo aesVo = new AesVo();
        aesVo.setData(encodeData);
        aesVo.setTransferDate(DateNowUtil.getNowDate());
        return aesVo;
    }
}
