package com.esteban.framework.utils;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 手机号码加密
 *
 * @date 2012-12-3
 */
public class MobileEncoder {
    private static Log log = LogFactory.getLog(MobileEncoder.class);

    private static String[] key = null;
    private static int randomLength = 3;
    private static int suffixLength = 4;

    /** 密钥 */
    private static String[] randomKeys = null;
    /** 密钥个数 */
    private static int randomKeysTotal = 0;
    static {
        String keyStr = "KLd3e7A6BcCp4DlmEF5yGfoHIxJghM89NsOjPwQ1R2qSzTvnUVtuWXYiZ0bark";
        key = new String[keyStr.length()];
        for (int i = 0; i < keyStr.length(); i++) {
            key[i] = String.valueOf(keyStr.charAt(i));
        }

        randomKeys = new String[] {
                "4DlmEF5yKLd3e7pGfoHA6BcCVtuWIxJghsOjPwQ1R2q0barkSzTvnM89NUXYiZ", //0
                "6BcCp4KLd3e7AIxJghM89NsOjPwQDlmEF5yGfoH1R2qSzTvnUVtuWXYbarkiZ0", //1
                "d3e7KLA6BcCplmEF5yG4VtuWXYiDfoHIxJ89NsSzOjPwQghM0bar1R2qTvnUZk", //2
                "barkKLdA6foHIxBcCp4DZ0lm3e7EF5yGsOjJ8WXY9NPwQ1R2qghnUMSzTvVtui", //3
                "KLd3e7ANsOj6BcCpyGfoHISzTarvnxJghM84DlmEF59PwQ1R2qXYikZ0UVtuWb", //4
                "7sOKLd3ejPwQA6BcCpHIxJgh4DlmEF5yZ0bkaGfoM8zTvn9N1RWXYi2qSUVtur", //5
                "dK3eL7A6BcCZ0bap4DlPwQ1RmEF5yGf89NsoHIxrkJghMOjnUVtu2qSzTvWXYi", //6
                "d3e7AKL6Bc4DlmEF5CpyGfoHIxJgsOjPwQ1R2qSzhM89NTvuarWtbkXYiZ0nUV", //7
                "KLd3e7A6BcCp4DlmEF5yGfoHIxJghM89NsOjPwQ1R2qSzTvnUVrktuWXYiZ0ba", //8
                "Ld3eK7A6BcCGfop4DlmEF5yHIxjPwQJghM89NsO1R2qSzTvnUVZ0barktuWXYi", //9
        };
        randomKeysTotal = randomKeys.length;
    }

    /**
     * 加密算法<br>
     *
     * @since 2013年8月11日
     */
    public class Encoder {
        private String[] useKey = null;
        private int useKeyLength = 0;
        private String random;

        public Encoder() {
            random = getRandom(randomLength);
            useKey = getRandomKey(random);
            useKeyLength = useKey.length;
        }

        /**
         * 加密随机码
         *
         * @param random
         * @return String
         * @since 2013年8月11日
         */
        private String encodeRandom(String random) {
            String retStr = key[Integer.valueOf(String.valueOf(random.charAt(0)))];
            for (int i = 1; i < random.length(); i++) {
                int a = Integer.parseInt(random.substring(i - 1, i + 1));
                if (a >= key.length) {
                    retStr += key[a % key.length];
                } else {
                    retStr += key[a];
                }
            }
            return retStr;
        }

        /**
         * 用随机密钥进行加密数字串
         *
         * @param source 由数字组成的字符串
         * @return
         * @throws Exception String
         * @since 2013年8月11日
         */
        private String encode(String source) throws Exception {
            String retStr = useKey[Integer.valueOf(String.valueOf(source.charAt(0)))];
            for (int i = 1; i < source.length(); i++) {
                int a = Integer.parseInt(source.substring(i - 1, i + 1));
                if (a >= useKeyLength) {
                    retStr += useKey[a % useKeyLength];
                } else {
                    retStr += useKey[a];
                }
            }
            return retStr;
        }

        /**
         * 加密
         *
         * @param num 数字编号
         * @param mobile 手机号码（仅取最后4位）
         * @return
         * @throws Exception String
         * @since 2013年8月11日
         */
        public String encode(int num, String mobile) throws Exception {
            String suffix = mobile.substring(mobile.length() - suffixLength);
            String encodeRandom = encodeRandom(random);
            String encodeSuffix = encode(suffix);
            long a = Long.parseLong(random) + Long.parseLong(suffix) + num;
            String encodeA = encode(String.valueOf(a));

            log.info("IN>>" + num + ", " + mobile + "; OUT>>" + suffix + "->" + encodeSuffix + "," + a + "->" + encodeA + "," + random + "->" + encodeRandom);
            return encodeSuffix + encodeA + encodeRandom;
        }
    }

    /**
     * 解密<br>
     *
     * @since 2013年8月11日
     */
    public class Decoder {
        private String[] useKey = null;
        private String random;

        private int num;
        private String suffix;

        /**
         * 解密
         *
         * @param source
         * @return String
         * @since 2013年8月9日
         */
        private String decodeRandom(String str) {
            String lastNum = getIndexFromKey(key, str.substring(0, 1));
            String random = lastNum;

            for (int i = 1; i < str.length(); i++) {
                lastNum = getIndexFromKey(key, lastNum, str.substring(i, i + 1));
                random += lastNum;
            }
            return random;
        }

        /**
         * ID解密
         *
         * @param source
         * @param key
         * @return String
         * @since 2013年8月9日
         */
        public int decode(String source) {
            String randomEncodeStr = source.substring(source.length() - randomLength);
            String suffixEncodeStr = source.substring(0, suffixLength);
            String othersEncodeStr = source.substring(suffixLength, source.length() - randomLength);
            random = decodeRandom(randomEncodeStr);
            if (random != null && !"".equals(random)) {
                this.useKey = getRandomKey(random);
                //解密手机号码尾号
                suffix = decode(this.useKey, suffixEncodeStr);
                //解密编号
                String others = decode(this.useKey, othersEncodeStr);
                num = Integer.parseInt(others) - Integer.parseInt(suffix) - Integer.parseInt(random);
                return num;
            }
            return 0;
        }

        /**
         * 验证解密是否正确
         *
         * @param mobile 手机号码
         * @return boolean
         * @since 2013年8月11日
         */
        public boolean checkDecode(int num, String mobile) {
            if (mobile != null && mobile.length() >= 11) {
                if (suffix != null && suffix.length() == suffixLength) {
                    if (suffix.equals(mobile.substring(mobile.length() - suffixLength)) && num == this.num) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 解密
         *
         * @param source
         * @return String
         * @since 2013年8月9日
         */
        private String decode(String[] key, String str) {
            String lastNum = getIndexFromKey(key, str.substring(0, 1));
            String sumStr = lastNum;

            for (int i = 1; i < str.length(); i++) {
                lastNum = getIndexFromKey(key, lastNum, str.substring(i, i + 1));
                sumStr += lastNum;
            }
            return sumStr;
        }

        private String getIndexFromKey(String[] key, String c) {
            for (int i = 0; i < key.length; i++) {
                if (key[i].equals(c)) {
                    return String.valueOf(i);
                }
            }
            return "";
        }

        private String getIndexFromKey(String[] key, String lastNum, String c) {
            int index = Integer.parseInt(getIndexFromKey(key, c));
            String ret = String.valueOf(index);
            String retNum = String.valueOf(ret.charAt(ret.length() - 1));
            int t = Integer.valueOf(lastNum + retNum);
            if (t != index) {
                ret = String.valueOf(index + key.length);
                retNum = String.valueOf(ret.charAt(ret.length() - 1));
            }
            return retNum;
        }
    }

    /**
     * 获取随机码
     *
     * @param len 随机码长度
     * @return String
     * @since 2013年8月11日
     */
    public static String getRandom(int len) {
        Random r = new Random();
        String result = "";
        for (int i = 0; i < len; i++) {
            result += r.nextInt(9);
        }
        return result;
    }

    /**
     * 获取有效的密钥索引
     *
     * @param num
     * @return int
     * @since 2013年8月11日
     */
    private int getRandomKeyIndex(int num) {
        return num % randomKeysTotal;
    }

    /**
     * 获取当前有效的密钥
     *
     * @param index
     * @return String
     * @since 2013年8月11日
     */
    private String getRandomKey(int keyIndex) {
        if (keyIndex >= randomKeysTotal) {
            return null;
        }
        return randomKeys[keyIndex];
    }

    private String[] getRandomKey(String source) {
        String[] keys = null;
        if (source != null && source.length() > 0) {
            int num = Integer.parseInt(source);
            String key = getRandomKey(getRandomKeyIndex(num));
            keys = new String[key.length()];
            for (int i = 0; i < key.length(); i++) {
                keys[i] = String.valueOf(key.charAt(i));
            }
            return keys;
        }
        return null;
    }
}
