package com.esteban.core.framework.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * Created by CPR269 on 2018/5/25.
 */
public class HmacUtils {

    /**
     * MAC算法可选以下多种算法
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    static final String ALGORITHM_MAC = "HmacMD5";
    static final String ALGORITHM_MD5 = "MD5";
    static final String ALGORITHM_SHA = "SHA";

    /** 密钥 **/
    static final String MAC_KEY = "J8edBdyF6SrQRmkwTkAPD8tY31yt33";

    /**
     * MD5加密
     * @param source
     * @return
     * @throws Exception
     */
    static String encryptionMD5(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
        md.update(source.getBytes());
        return byteArrayToHexString(md.digest());
    }

    /**
     * SHA加密
     * @param source
     * @return
     * @throws Exception
     */
    static String encryptionSHA(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA);
        md.update(source.getBytes());
        return byteArrayToHexString(md.digest());
    }

    /**
     * HMAC加密
     * @return
     * @throws Exception
     */
    static String encryptionHMAC(String source) throws Exception {
        SecretKey secretKey = new SecretKeySpec(MAC_KEY.getBytes(), ALGORITHM_MAC);
        Mac mac = Mac.getInstance(ALGORITHM_MAC);
        mac.init(secretKey);
        mac.update(source.getBytes());
        return byteArrayToHexString(mac.doFinal());
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * base64编码
     * @param source
     * @return
     * @throws Exception
     */
    static String encodeBase64(byte[] source) throws Exception{
        return Base64Utils.base64Encode(new String(source,"UTF-8"));
    }

}
