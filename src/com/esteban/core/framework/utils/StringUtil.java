package com.esteban.core.framework.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 */
public class StringUtil {

	public static final String CHARSET_NAME = "UTF-8";
	public static final String SPLIT_CHARS="@";
	/**
	 * 根据传入的字符串生成字符串集合
	 * 
	 * @param val
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public static List<String> stringToList(String val) {
		if (val != null) {
			String[] list = val.split("[ ]*,[ ]*");
			return Arrays.asList(list);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * 转换对象为字符串,如果对象为空，返回为""
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String nvl(Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		return obj.toString();
	}

	/**
	 * 检查字符串是否不为空
	 * 
	 * @param obj
	 *            a string to check
	 * @return true if the string is not empty
	 */
	public static boolean isNotBlank(Object obj) {
		if (!nvl(obj).trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @param obj
	 *            a string to check
	 * @return true if the string is empty
	 */
	public static boolean isBlank(Object obj) {
		return !isNotBlank(obj);
	}

	/**
	 * 计算中文字的个数
	 * 
	 * @author tyun
	 * @since 2012/12/20
	 */
	public static int chineseCount(String word) {
		int v = 0;
		char c;
		for (int cc = 0; cc < word.length(); cc++) {
			c = word.charAt(cc);
			if (!(c >= 32 && c <= 126))
				v++;
		}
		return v;
	}

	/**
	 * 首字符前面补空格
	 * 
	 * @param val
	 *            值
	 * @param length
	 *            补足长度
	 * @return String
	 */
	public static String padLeftSpace(String val, int length) {

		return padSpace(val, length, CHARSET_NAME, true);
	}

	/**
	 * 尾数后补空格
	 * 
	 * @param val
	 *            值
	 * @param length
	 *            补足长度
	 * @return String
	 */
	public static String padRightSpace(String val, int length) {

		return padSpace(val, length, CHARSET_NAME, false);
	}

	/**
	 * 字符串添加空格
	 * 
	 * @param val
	 *            值
	 * @param length
	 *            变换int用文字数生成
	 * @param charsetName
	 *            编码格式
	 * @param isLeft
	 *            true：左边补空格（首字符前），false：右边补空格（尾数后）
	 * @return String
	 */
	public static String padSpace(String val, int length, String charsetName,
								  boolean isLeft) {

		if (val == null) {
			return "";
		}

		int num = 0;
		String sRet = "";
		try {
			num = length - val.getBytes(charsetName).length;
			if (num <= 0) {
				return val;
			}

			StringBuffer result = new StringBuffer();
			for (int i = 0; i < num; i++) {
				result.append((char) 32);
			}

			if (isLeft == true) {
				sRet = result.toString() + val;
			} else {
				sRet = val + result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sRet;
	}

	/**
	 * 首字符前面补0
	 * 
	 * @param val
	 *            值
	 * @param length
	 *            补足长度
	 * @return String
	 */
	public static String padLeftZero(String val, int length) {

		return padZero(val, length, CHARSET_NAME, true);
	}

	/**
	 * 字符串添加0
	 * 
	 * @param val
	 *            值
	 * @param length
	 *            变换int用文字数生成
	 * @param charsetName
	 *            编码格式
	 * @param isLeft
	 *            true：左边补0（首字符前），false：右边补0（尾数后）
	 * @return String
	 */
	public static String padZero(String val, int length, String charsetName,
								 boolean isLeft) {

		if (val == null) {
			return "";
		}

		int num = 0;
		String sRet = "";
		try {
			num = length - val.getBytes(charsetName).length;
			if (num <= 0) {
				return val;
			}

			StringBuffer result = new StringBuffer();
			for (int i = 0; i < num; i++) {
				result.append((char) 48);
			}

			if (isLeft == true) {
				sRet = result.toString() + val;
			} else {
				sRet = val + result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sRet;
	}

	/**
	 * 计算字符串的长度. 一个字符长度为1，一个汉字的长度为2.
	 * 
	 * @param value
	 * @return int
	 */
	public static int lengthOfString(String value) {
		if (value == null)
			return 0;
		StringBuffer buff = new StringBuffer(value);
		int length = 0;
		String stmp;
		for (int i = 0; i < buff.length(); i++) {
			stmp = buff.substring(i, i + 1);
			try {
				stmp = new String(stmp.getBytes(CHARSET_NAME));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (stmp.getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * 将字符串转为整型字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String str2IntStr(String value) {

		int iRet = 0;
		try {
			value = isBlank(value) ? "0" : value;
			iRet = Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "" + iRet;
	}

	/**
	 * 判断字符串是否仅含英文或数字，不含中文
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEnglish(String val) {
		
		if (isBlank(val)) {
			return false;
		}
		return val.getBytes().length == val.length();
	}
	
	public static String joinParamsFit(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append("?,");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	/**
	 * 将字符串中连续的多个空格替换成一个空格
	 * @param str
	 * @return
	 */
	public static String repaceWhiteSapce(String str){
		String sb = "";
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(str);
		sb= m.replaceAll(" ");
	    return sb;
	}

	/**
	 * 判断某个字符串的位置
	 * @param arr
	 * @param str
	 * @return
	 */
	public static int index(String[] arr, String str) {
		int j = 0;
		for (int i=0; i < arr.length; i++) {
			if (str.equals(arr[i])) {
				j = i;
			}
		}
		return j;
	}

    public static String formatPhoneAndMobile(String number){
        Pattern mobilePattern = Pattern.compile("1\\d{10}");
        Pattern phonePattern = Pattern.compile("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7}){1}(\\-[0-9]{1,4})?");
        if(Utility.isEmpty(number)){
            number = "";
        }
        char[] result = number.toCharArray();
        if(mobilePattern.matcher(number).matches()){
            // 如果是手机号
            for(int i=0; i< 4; i++){
                result[3+i] = "*".charAt(0);
            }
            return  String.valueOf(result);
        } else if(phonePattern.matcher(number).matches()){
            // 如果是固定电话
            for(int i=number.length() - 4 ; i< number.length(); i++){
                result[i] = "*".charAt(0);
            }
            return String.valueOf(result);
        } else {
            return number;
        }
    }

	public static String join(String[] args, String split, boolean isNeedYinHao) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		if(isNeedYinHao)
			for(String s:args){
				sb.append("'").append(s).append("'").append(split);
			}
		else
			for(String s:args){
				sb.append(s).append(split);
			}
		return sb.substring(0, sb.length()-1);
	}

    public static void main(String[] args) {
        System.out.println(formatPhoneAndMobile("13720296905"));
        System.out.println(formatPhoneAndMobile("87296441"));
        System.out.println(formatPhoneAndMobile("0722-87296445"));
        System.out.println(formatPhoneAndMobile("010-87296445"));
        System.out.println(formatPhoneAndMobile("010-87296445-1256"));
        System.out.println(formatPhoneAndMobile("010-87296445-125678"));
        System.out.println(formatPhoneAndMobile(null));
        System.out.println(formatPhoneAndMobile(""));
    }
}
