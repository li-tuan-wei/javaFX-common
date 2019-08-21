package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.date.DateNowUtil;
import ldh.common.spring.security.cryptogram.RsaUtil;
import ldh.common.spring.security.service.vo.RsaParam;
import ldh.common.spring.security.service.vo.RsaVo;
import org.springframework.stereotype.Service;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Service
public class RsaSingleService extends RsaService {

    @Override
    public RsaVo encode(Object data, String initData) throws SecurityException {
        try {
            RsaParam rsaParam = parse(initData);
            String srcData = data.toString();
            if (!(data instanceof String)) {
                srcData = JsonViewFactory.create().toJson(data);
            }
            String encodeData = encodeByPrivateKey(srcData, rsaParam.getPrivateKey());
            RsaVo rsaVo = new RsaVo();
            rsaVo.setData(encodeData);
            rsaVo.setTransferDate(DateNowUtil.getNowDate());
            return rsaVo;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
