package com.esteban.core.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by CPR269 on 2018/5/25.
 */
public class Base64Utils {

    /**
     * 传入字符串，编码成base64
     * @param str
     * @return
     */
    public static String base64Encode(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 传入字符串，解析base64
     * @param str
     * @return
     */
    public static String base64Decode(String str){
        try {
            return new String(Base64.getDecoder().decode(str),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
