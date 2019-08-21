package ldh.common.spring.security.service;

import ldh.common.spring.security.MerchantInfo;
import ldh.common.spring.security.Merchantable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh123 on 2018/10/13.
 */
public class DbMerchantService implements Merchantable{

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public MerchantInfo getMerchantInfo(String merchantNo) {
        String sql = "select validate_service, init_data from merchant where merchant_no = ? and status = 1";
        List<Map> merchantInfoes = jdbcTemplate.queryForList(sql, Map.class, merchantNo);
        Map<String, Object> data = merchantInfoes.size() > 0 ? merchantInfoes.get(0) : null;
        if (data == null) return null;
        return new MerchantInfo(data.get("validate_service").toString(), data.get("init_data").toString());
    }
}
