package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.date.DateNowUtil;
import ldh.common.spring.security.cryptogram.AesUtil;
import ldh.common.spring.security.cryptogram.RsaUtil;
import ldh.common.spring.security.service.vo.RsaAesParam;
import ldh.common.spring.security.service.vo.RsaAesVo;
import org.springframework.stereotype.Service;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Service
public class RsaAesService extends DefaultSecurityService<RsaAesVo, RsaAesParam> {

    /**
     * 客户端加解密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public String[] encodeByPublicKey(String data, String publicKey) throws Exception {
        String aesKey = AesUtil.createKey();
        String encodeAesKey = RsaUtil.encryptByPublicKey(aesKey, publicKey);
        String encodeData = AesUtil.encrypt(data, aesKey);
        return new String[]{encodeData, encodeAesKey};
    }

    public String decodeByPublicKey(String decodeData, String decodeAesKey, String publicKey) throws Exception {
        String aesKey = RsaUtil.decryptByPublicKey(decodeAesKey, publicKey);
        String srcData = AesUtil.decrypt(decodeData, aesKey);
        return srcData;
    }

    /**
     * 服务器端加解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public String[] encodeByPrivateKey(String data, String privateKey) throws Exception {
        String aesKey = AesUtil.createKey();
        String encodeAesKey = RsaUtil.encryptByPrivateKey(aesKey, privateKey);
        String encodeData = AesUtil.encrypt(data, aesKey);
        return new String[]{encodeData, encodeAesKey};
    }

    public String decodeByPrivateKey(String decodeData, String decodeAesKey, String privateKey) throws Exception {
        String aesKey = RsaUtil.decryptByPrivateKey(decodeAesKey, privateKey);
        String srcData = AesUtil.decrypt(decodeData, aesKey);
        return srcData;
    }

    @Override
    public String validate(String data, String initData) throws SecurityException {
        try {
            RsaAesParam rsaAesParam = parse(initData);
            RsaAesVo rsaAesVo = JsonViewFactory.create().fromJson(data, RsaAesVo.class);
            String srcData = decodeByPrivateKey(rsaAesVo.getData(), rsaAesVo.getEncodeAesKey(), rsaAesParam.getPrivateKey());
            return srcData;
        } catch (Exception ex) {
            throw new SecurityException(ex);
        }
    }

    @Override
    public RsaAesVo encode(Object data, String initData) throws SecurityException {
        try {
            RsaAesParam rsaAesParam = parse(initData);
            String srcData = data.toString();
            if (!(data instanceof String)) {
                srcData = JsonViewFactory.create().toJson(data);
            }
            String[] encodeData = encodeByPublicKey(srcData, rsaAesParam.getPublicKey());
            RsaAesVo rsaAesVo = new RsaAesVo();
            rsaAesVo.setData(encodeData[0]);
            rsaAesVo.setEncodeAesKey(encodeData[1]);
            rsaAesVo.setTransferDate(DateNowUtil.getNowDate());
            return rsaAesVo;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
