package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.date.DateNowUtil;
import ldh.common.spring.security.cryptogram.AesUtil;
import ldh.common.spring.security.cryptogram.RsaUtil;
import ldh.common.spring.security.service.vo.RsaAesParam;
import ldh.common.spring.security.service.vo.RsaAesVo;
import ldh.common.spring.security.service.vo.RsaParam;
import ldh.common.spring.security.service.vo.RsaVo;
import org.springframework.stereotype.Service;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Service
public class RsaService extends DefaultSecurityService<RsaVo, RsaParam> {

    /**
     * 客户端加解密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public String encodeByPublicKey(String data, String publicKey) throws Exception {
        String encodeData = RsaUtil.encryptByPublicKey(data, publicKey);
        return encodeData;
    }

    public String decodeByPublicKey(String decodeData, String publicKey) throws Exception {
        String srcData = RsaUtil.decryptByPublicKey(decodeData, publicKey);
        return srcData;
    }

    /**
     * 服务器端加解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public String encodeByPrivateKey(String data, String privateKey) throws Exception {
        String encodeData = RsaUtil.encryptByPrivateKey(data, privateKey);
        return encodeData;
    }

    public String decodeByPrivateKey(String decodeData, String privateKey) throws Exception {
        String srcData = RsaUtil.decryptByPrivateKey(decodeData, privateKey);
        return srcData;
    }

    @Override
    public String validate(String data, String initData) throws SecurityException {
        try {
            RsaParam rsaParam = parse(initData);
            RsaVo rsaVo = JsonViewFactory.create().fromJson(data, RsaVo.class);
            String srcData = decodeByPrivateKey(rsaVo.getData(), rsaParam.getPrivateKey());
            return srcData;
        } catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    @Override
    public RsaVo encode(Object data, String initData) throws SecurityException {
        try {
            RsaParam rsaParam = parse(initData);
            String srcData = data.toString();
            if (!(data instanceof String)) {
                srcData = JsonViewFactory.create().toJson(data);
            }
            String encodeData = encodeByPublicKey(srcData, rsaParam.getPublicKey());
            RsaVo rsaVo = new RsaVo();
            rsaVo.setData(encodeData);
            rsaVo.setTransferDate(DateNowUtil.getNowDate());
            return rsaVo;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
