package common;

import ldh.common.spring.security.cryptogram.AesUtil;
//import ldh.common.testui.assist.template.beetl.BeetlFactory;
//import ldh.common.testui.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class CommonHelp {

    public String md5(String str) {
        return Md5Util.md5(str, "adfafsadfaf");
    }

    public String aesEncode(String str) {
        return AesUtil.encrypt(str, "adfafsaddddddfaf");
    }

    public String aesDecode(String str) {
        return AesUtil.decrypt(str, "adfafsaddddddfaf");
    }

    public static void main(String[] args) throws Exception {
        CommonHelp help = new CommonHelp();
        String s = help.aesEncode("hello");
        String ds = help.aesDecode(s);
        System.out.println("ss:" + ds);

        Map<String, Object> paramMap = new HashMap<>();

        String el = "${Json.jsonPathValue(CommonHelp.aesDecode(Json.jsonPathValue(result, 'data')), 'hello')}";
                el =    "${Json.jsonPathValue(CommonHelp.aesDecode(Json.jsonPathValue(result, 'data')), 'hello') == 'hello world'}";
//        el = "${Json.jsonPathValue(result, 'data') == 'hello world'}";
        Map<String, Object> paramMap2 = new HashMap();
        paramMap2.put("data", "xxTjvIA0KonkRjSxkEcqLrA9xi/OGnI00N2Tu9t9XqU=");
//        String result = JsonUtil.toJson(paramMap2);
//        paramMap.put("result", result);
//        BeetlFactory.getInstance().addVarClass("CommonHelp", new CommonHelp());
//        String elValue = BeetlFactory.getInstance().process(el, paramMap);
//        System.out.println("elValue:" +elValue);
    }
}
