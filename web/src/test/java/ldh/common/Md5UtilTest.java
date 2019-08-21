package ldh.common;

import ldh.common.spring.security.cryptogram.AesUtil;
import ldh.common.spring.security.cryptogram.Md5Util;
import ldh.common.spring.security.cryptogram.RsaUtil;
import org.junit.Test;

/**
 * Created by ldh123 on 2018/10/14.
 */
public class Md5UtilTest {

    @Test
    public void test() {
        String md5Token = Md5Util.md5("sdfadfasdfa", "adfafsadfaf");
        System.out.println("dd:" + md5Token);
    }

    @Test
    public void aesTest() {
        String md5Token = AesUtil.encrypt("sdfadfasdfa", "adfafsadfaf");
        System.out.println("dd:" + md5Token);
    }

    @Test
    public void rsaAesTest() throws Exception {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyIyjvfv2qF8Q/2+at8Iy67PnQ56oZVUMrssZL2w+CXzPgC00UD8aZFx2LBXU+tT0mRO/hWcxf8HE2Yb2IXko9zGAjmSYIWQ54c7x8XkFbHaeg3hwvY+nMa5LRt1I/xLA+dKAGGojFvwCXefaLQVi+/TQ6OyTUN0LsJbeF/jb+0QIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIjKO9+/aoXxD/b5q3wjLrs+dDnqhlVQyuyxkvbD4JfM+ALTRQPxpkXHYsFdT61PSZE7+FZzF/wcTZhvYheSj3MYCOZJghZDnhzvHxeQVsdp6DeHC9j6cxrktG3Uj/EsD50oAYaiMW/AJd59otBWL79NDo7JNQ3Quwlt4X+Nv7RAgMBAAECgYAK++IFSnZHq6l9KokBxqhWyk/pEq/JWhmHM0HI4ZwGNOqU8P1ytDpRrDw1G0QMds5rcqq74XaZn9DLQqGWLAI1zPNq8daF/hMOcckhCFwhtoFpgh5Qblr4d2TnAlL7GMkP9n7C9+GD+N953C347msr9d6wbCOBD/QmkVv5KFpa8QJBANaNUOM9rkDGYDa++NaeXi7bh/xWGuCK2iLxmZEiSe+l0EnVbn7Q9ceoe6WMCaKg3R47ZHCTNF9wY/K357bNkB0CQQDUjPQ09ZpbnsnyY+ZjpKFqAaCsEErTW3kyj3Sie2Jd3jo/ZdXXXRqBW1tD/LZt/r9n1OWTlcr0smFVPKfTARNFAkBtJZ8Mu4g2M7UmpKh6pj5LoCrh1/v8Vyb5t+bjyonKfJQlu4pmwW8Hj1K3uTH9pdBXXOePzZNLKiXuiFg8yv6RAkEAgoUb4PmAlApqEEJwU3fg9uuQ8pf+mtNtFTMqeCw0N1gjhJLQQbOWdyDWZmVk0R7CAsylPX+aZbC0PFdG5zed0QJBALUSmoKbHH9P3rW/Hd1pYfSmhuXMIktWE1hdY0xbkSViIBTfOJib+iHDKugfVUGP+zr0zgpyFY/XP7onHTCiAXA=";
        String md5Token = RsaUtil.encryptByPublicKey("adfafsadfaf", publicKey);
        System.out.println("dd:" + md5Token);

        String str = "乙方向甲方发送数据RSA算法";
        String code2 = RsaUtil.encryptByPublicKey(str, publicKey);
        String decode2 = RsaUtil.decryptByPrivateKey(code2, privateKey);
        System.out.println("str:" + decode2);
        System.out.println("str:" + str.equals(decode2));

        String aesKey = AesUtil.createKey();
        System.out.println("aesKey:" + aesKey);
        String encodeAesKey = RsaUtil.encryptByPublicKey(aesKey, publicKey);
        String encodeData = AesUtil.encrypt("sadfasfadfasfasfas", aesKey);
        System.out.println("dd1:" + encodeAesKey);
        System.out.println("dd2:" + encodeData);

        String aesKey2 = RsaUtil.decryptByPrivateKey(encodeAesKey, privateKey);
        System.out.println("aesKey2:" + aesKey2);
        System.out.println("eee:" + aesKey.equals(aesKey2));
        String data = AesUtil.decrypt(encodeData, aesKey2);
        System.out.println("eee2:" + data);
    }
}
