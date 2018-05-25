package com.esteban.core.framework.utils;

/**
 * Created by CPR269 on 2018/5/25.
 */
public class TokenUtils {

    private static String SECRET_KEY="J8edBdyF6SrQRmkwTkAPD8tY31yt33";

    public static String getToken(String userCode,String derviceCode){
        String token="";

        //生成header
        String header="{'typ':'JWT','alg':'HS256'}";
        String header_base64= Base64Utils.base64Encode(header);

        //生成payload
        String payload="{'iss':'esteban','sub':'"+userCode+"','exp':'1800s','iat':'"+DateOperator.getNowTimeStamp()+"'}";
        String payload_base64=Base64Utils.base64Encode(payload);

        //生成signature
        try {
            String secret = HmacUtils.encryptionHMAC(header_base64+"."+payload_base64+SECRET_KEY);
            token = header_base64+"."+payload_base64+"."+secret;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

}
