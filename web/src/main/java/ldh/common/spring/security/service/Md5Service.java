package ldh.common.spring.security.service;

import ldh.common.json.JsonViewFactory;
import ldh.common.spring.date.DateNowUtil;
import ldh.common.spring.security.cryptogram.Md5Util;
import ldh.common.spring.security.service.vo.Md5Param;
import ldh.common.spring.security.service.vo.Md5Vo;
import org.springframework.stereotype.Service;

/**
 * Created by ldh123 on 2018/10/13.
 */
@Service
public class Md5Service extends DefaultSecurityService<Md5Vo, Md5Param> {

    public boolean checkMd5(String srcData, String md5Token, String salt) {
        String md5Token2 = Md5Util.md5(srcData, salt);
        return md5Token2.equals(md5Token);
    }

    public String md5(String data, String salt) {
        return Md5Util.md5(data, salt);
    }

    @Override
    public String validate(String data, String initData) {
        Md5Param md5Param = parse(initData);
        Md5Vo md5Vo = JsonViewFactory.create().fromJson(data, Md5Vo.class);
        boolean isSuccess = checkMd5(md5Vo.getData(), md5Vo.getMd5Token(), md5Param.getSalt());
        if (isSuccess) {
            return md5Vo.getData();
        }
        throw new RuntimeException("非法数据");
    }

    @Override
    public Md5Vo encode(Object data, String initData) throws SecurityException {
        Md5Param md5Param = parse(initData);
        String srcData = data.toString();
        if (!(data instanceof String)) {
            srcData = JsonViewFactory.create().toJson(data);
        }
        String md5Data = md5(srcData, md5Param.getSalt());
        Md5Vo md5Vo = new Md5Vo();
        md5Vo.setData(srcData);
        md5Vo.setMd5Token(md5Data);
        md5Vo.setTransferDate(DateNowUtil.getNowDate());
        return md5Vo;
    }
}
