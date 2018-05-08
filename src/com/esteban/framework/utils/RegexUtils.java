package com.esteban.framework.utils;

import java.util.regex.Pattern;

/**
 * @author esteban
 * @since 2014年4月29日
 */
public class RegexUtils {
    /**
     * 判断是否手机号码
     *
     * @param mobile
     * @return boolean
     * @since 2014年3月6日
     */
    public static boolean isMobile(String mobile) {
        //String pattern = "^0{0,1}1[3,5,8]\\d{9}$";// 所有手机号码
        String pattern = "^0{0,1}1(33|53|8[019])\\d{8}$"; //电信号码
        //String pattern = "^0{0,1}1(3[012]|45|5[56]|8[56])\\d{8}$"; //联通号码
        // String pattern = "^0{0,1}1(3{4-9}|47|5[012789]|8[2378])\\d{8}$"; //移动号码
        return Pattern.matches(pattern, mobile);
    }
}
