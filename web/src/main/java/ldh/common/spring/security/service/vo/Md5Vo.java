package ldh.common.spring.security.service.vo;

/**
 * Created by ldh123 on 2018/10/13.
 */
public class Md5Vo extends BaseVo {

    private String md5Token;

    public String getMd5Token() {
        return md5Token;
    }

    public void setMd5Token(String md5Token) {
        this.md5Token = md5Token;
    }
}
