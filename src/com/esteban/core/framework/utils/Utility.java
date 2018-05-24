package com.esteban.core.framework.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//定义公共静态方法
public class Utility {
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static java.util.Random rndGenerator = new java.util.Random();
	
	public static int toInt(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Integer.parseInt(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Long.parseLong(_strData);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static Short toShort(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Short.parseShort(_strData);
		} catch (Exception e) {
			return 0;
		}
	}	

	public static double toDouble(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Double.parseDouble(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static float toFloat(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Float.parseFloat(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean toBoolean(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return false;
			if (_strData.startsWith("0")
					|| _strData.toLowerCase().startsWith("false"))
				return false;
			else
				return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static BigDecimal toBigDecimal(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return new BigDecimal("0");
			return new BigDecimal(_strData);
		} catch (Exception e) {
			return new BigDecimal("0");
		}
	}
	
	public static String byte2hex(byte[] _buffer) {
		String strHex = "";
		String strTemp = "";
		for (int i = 0; i < _buffer.length; i++) {
			strTemp = (java.lang.Integer.toHexString(_buffer[i] & 0XFF));
			if (strTemp.length() == 1)
				strHex = strHex + "0" + strTemp;
			else
				strHex = strHex + strTemp;
			if (i < _buffer.length - 1)
				strHex = strHex + ":";
		}
		return strHex.toUpperCase();
	}
	
	/**
	 * 字符串小数转换为四舍五入并保留两位
	 * @param str
	 * @return String
	 */
	public static String getFloatTwo(String str){
		if(str==null ||str.trim().equals("")){
			str=  "";
		}else{
			double num = Double.parseDouble(str);
			BigDecimal b = new BigDecimal(num);
			num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			str = Double.toString(num);
		}
		return str;
	}
	
	/**
	 * Bean属性拷贝
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		try {
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static String firstToUpper(String str1) {
		String str2 = str1.toUpperCase();
		if (str1.length() > 1) {
			str2 = str1.toLowerCase().substring(0, 1).toUpperCase()
					+ str1.toLowerCase().substring(1);
		}
		return str2;
	}	
	
	public static String replaceLine(String fieldName) {
		String filenametemp = fieldName;
		if (filenametemp.indexOf("_") > 0) {
			String[] filenamenew = filenametemp.split("_");
			for (int j = 0; j < filenamenew.length; j++) {
				if (j == 0) {
					filenametemp = filenamenew[j];
				} else {
					filenametemp = filenametemp + firstToUpper(filenamenew[j]);
				}
			}
		}
		return filenametemp;
	}	
	
	public static boolean isNotEmpty(Object obj)
	{
    	return !isEmpty(obj);
	}
	
	public static boolean isEmpty(Object obj)
	{
    	boolean result = false;
    	if(obj == null ){
    		result = true;
    	}
    	String str = String.valueOf(obj);
    	if(str == null || str.trim().length() <=0){
    		result = true;
    	} else {
    		if("NULL".equals(str.toUpperCase()) && !(obj instanceof String)){
    			result = true;
    		}
    	}
    	return result;
	}

	/**
	 * 验证输入的参数是否完全
	 * @param parameters
	 * @param inputData
	 * @return
	 */
	public static boolean validRequestPara(String[] parameters, JSONObject inputData){
		if(inputData != null && parameters != null && parameters.length > 0){
			for(String para : parameters){
				if(!inputData.containsKey(para)){
					return false;
				}
			}
		}
		return true;
	}
	
	public static String getSenNo(int len, String value){
		String result ="";
		if(Utility.isEmpty(value)){
			value ="0";
		}
		if(len < value.length()){
			result = value;
		} else {
			value = String.valueOf(Long.valueOf(value)+1);
			result = value;
			for(int i=0;i<len-value.length();i++){
				result = "0"+result;
			}
		}
		return result;
	}
	
	public static String createRnd() {
		int iTemp = rndGenerator.nextInt(10000);
		if (iTemp < 1000)
			iTemp += 1000;
		else if (iTemp == 10000)
			iTemp = 9999;
		return Integer.toString(iTemp);
	}	
	
	/**
	 * 根据Key从MAP获取值
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValueByKey(Map map, String key, String defaultValue){
		String result = defaultValue;
		if(map != null){
			try{
				String rt = (String)map.get(key);
				if(!Utility.isEmpty(rt)){
					result = rt;
				}
			} catch(Exception e){
			}
		}
		return result;
	}	
	
	public static Object mapToBean(Object object, Map map){
		if(map != null && map.size() > 0){
			try{
				PropertyDescriptor[] porperties = BeanUtils.getPropertyDescriptors(object.getClass());
				if(porperties != null && porperties.length > 0){
					for(PropertyDescriptor property:porperties){
						String porpertryName = property.getName();
						try{
			                if (map.containsKey(porpertryName)) {
			                    Object value = map.get(porpertryName);
			                    Class clz = property.getPropertyType();
			                    Method setter = property.getWriteMethod();
			                    if(clz.getName().indexOf("Long") !=-1){
			                    	Long longValue = Utility.toLong(String.valueOf(value));
			                    	setter.invoke(object, longValue);
			                    } else if(clz.getName().indexOf("Short") !=-1){
			                    	Short shortValue = Utility.toShort(String.valueOf(value));
			                    	setter.invoke(object, shortValue);
			                    } else if(clz.getName().indexOf("int") !=-1){
			                    	int intValue = Utility.toInt(String.valueOf(value));
			                    	setter.invoke(object, intValue);
			                    } else if(clz.getName().indexOf("Double") !=-1){
			                    	Double doubleValue = Utility.toDouble(String.valueOf(value));
			                    	setter.invoke(object, doubleValue);
			                    }  else if(clz.getName().indexOf("Float") !=-1){
			                    	Float floatValue = Utility.toFloat(String.valueOf(value));
			                    	setter.invoke(object, floatValue);
			                    }  else {
			                    	setter.invoke(object, value);
			                    }
			                }
						} catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			} catch(Throwable e){
			}
		}
		return object;
	}
	
	public static String getNvlStr(String value){
		String result = value;
		if(value == null || value.trim().length() <= 0){
			result ="";
		} else {
			if(Utility.getConverteStr(String.valueOf(value)).indexOf("null")!=-1)
			{
				result ="";
			}
		}
		result = getConverteStr(result);
		return result;
	}
	
	
	public static String getLowerStr(String value){
		String result ="";
		if(value != null && value.length() >0 ){
			result = value.toLowerCase();
		}
		return result;
	}

	public static String getConverteStr(String value){
		String result = value;
		if(Utility.isEmpty(value)){
			return result;
		}
		try{
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll("\"", "&quot;");
			result = result.replaceAll("\'", "&#39;");
			result = result.replaceAll("\\\\", "\\\\");
	        result = result.replaceAll("\n", "");
	        result = result.replaceAll("\r", "");
		} catch(Exception e){}
        return result;
	}

	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String formatNumberToString(String number, int count){
		return StringUtils.leftPad(number, count, "0");
	}
	
	public static Double getNegtiveDoubleValue(Double value){
		if(value == null){
			return 0d;
		} else {
			return -value;
		}
	}

	/**
	 * 特殊字符检查
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public static boolean isRightCharacter(String entityCode, String userName, String userPassword){
		boolean result = false;
		Pattern p= Pattern.compile("[\'|\"|%|$|@|+|;|<|>|(|)|\t|\n|\\|]");
		Matcher m=p.matcher(userName);
		result=m.find();
		if(result){
			return result;
		}
		m=p.matcher(userPassword);
		result=m.find();
		if(result){
			return result;
		}
		m=p.matcher(entityCode);
		result=m.find();
		if(result){
			return result;
		}
		return result;
	}
	
	
	public static String filterDangerString(String value) {
        if (value == null) {
			return null;
        }
        value = value.replaceAll("\\|", "");
        value = value.replaceAll("&", "&");
        value = value.replaceAll(";", "");
        value = value.replaceAll("@", "");
        value = value.replaceAll("'", "");
        //value = value.replaceAll("\"", "");
        value = value.replaceAll("\\'", "");
        //value = value.replaceAll("\\\"", "");
        value = value.replaceAll("<", "<");
        value = value.replaceAll(">", ">");
        value = value.replaceAll("\\(", "");
        value = value.replaceAll("\\)", "");
        value = value.replaceAll("\\+", "");
        value = value.replaceAll("\r", "");
        value = value.replaceAll("\n", "");
        value = value.replaceAll("script", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll(">", "");
        value = value.replaceAll("<", "");
        //value = value.replaceAll("=", "");
        value = value.replaceAll("/", "");
        //value = value.replaceAll("%", "");
        return value;
   }
	
   public static boolean isMobile(HttpServletRequest request){
	   boolean result = false;
	   String userAgent = request.getHeader("User-Agent");
	   if(Utility.isEmpty(userAgent)){
		   userAgent = request.getHeader("USER-AGENT");
	   } 
	   if(!Utility.isEmpty(userAgent)){
		   Pattern p= Pattern.compile("iphone|ipod|android|blackberry|webos|incognito|webmate|bada|nokia|lg|ucweb|skyfire");
		   Matcher m=p.matcher(userAgent.toLowerCase());
		   result=m.find();
	   }
	   return result;
   }
   
   public static Map getRequestHeaders(HttpServletRequest request){
	    Map map = new HashMap();
		Enumeration e = request.getHeaderNames() ;
		while(e.hasMoreElements()){  
			 String name = (String) e.nextElement();
			 String value = request.getHeader(name);
			 map.put(name, value);
		}
	    return map;
   }
   
   /**
    * 判断是否是整形
    * @param str
    * @return
    */
   public static boolean isInteger(String str){
	   try{
		   Integer.parseInt(str);
		   return true;
	   } catch (NumberFormatException e) {
		   return false;
	   }
   }
   
   /**
    * 验证是否是浮点型
    * @param str
    * @return
    */
   public static boolean isDouble(String str){
	   try{
		   Double.parseDouble(str);
		   if(str.contains(".")){
			   return true;
		   }
		   return false;
	   } catch (NumberFormatException e) {
		   return false;
	   }
   }
   
   /**
    * 验证是否是数字类型
    * @param str
    * @return
    */
   public static boolean isNumber(String str){
	   return isInteger(str) || isDouble(str);
   }
   
   // 验证是否是时间类型
   public static boolean isDate(String str){
		try {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   sdf.setLenient(false);
		   sdf.parse(str);
		   return true;
		} catch (ParseException e) {
			return false;
		}
   }
   

   /**
    * 分割字符串，按照num长度进行分割成字符串数组
    * @param str
    * @param num
    */
   public static List<String> splitStr(String str, int num){
	   str = str.replace(" ", "");
	   List<String> strs = new ArrayList<String>();
	   
	   if(num<1){
		   return null;
	   }
	   if(str.length()<=num){
		   strs.add(str);
	   }else{
		   int stop = str.length() - num + 1;
		   for(int i=0;i<stop;i++){
			   String tmpStr = str.substring(i,i+num);
			  strs.add(tmpStr);
		   }
	   }
	   
	   return strs;
   }
   
   public static String getRandomNum(int num){
	   double value = (Math.random() * 9 + 1) * Math.pow(10d, Double.valueOf(num - 1));
	   DecimalFormat decimalFormat = new DecimalFormat("0");
	   return decimalFormat.format(value);
   }
   
   public static double doubleAdd(Double... doubleVal){
	   double result = 0d;
	   for(int i=0; i<doubleVal.length; i++){
		   result += doubleVal[i];
	   }
	   BigDecimal b = new BigDecimal(result);
       b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
       result = b.doubleValue();
	   return result;
   }
   
   public static double doubleMul(double... doubleVal){
	   double result = 1d;
	   for(int i=0; i<doubleVal.length; i++){
		   result = result * doubleVal[i];
	   }
	   BigDecimal b = new BigDecimal(result);
       b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
       result = b.doubleValue();
	   return result;
   }
   
	/**
    * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
    *  
    * @param request 
    * @return 
    */
   public static String getIpAddress(HttpServletRequest request)  {
       String ip = request.getHeader("X-Forwarded-For");
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("Proxy-Client-IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_CLIENT_IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_X_FORWARDED_FOR");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getRemoteAddr();
           }
       }
       return ip;  
   }

	/**
	 * 至少存在一个为空
	 * @param args
	 * @return
     */
	public static boolean oneOfAnyIsNull(Object[] args){
		boolean flag = false;
		if(args==null||args.length==0){
			flag =true;
			return flag;
		}else{
			for(Object obj:args){
				if(obj==null){
					flag = true;
					break;
				}
			}
			return flag;
		}
	}

	public static double getDiv(Object fenZi, Object fenMu){
		if(fenZi==null||fenMu==null)
			return 0.00;
		if(fenZi instanceof Number && fenMu instanceof Number){
			Number a=(Number) fenZi;
			Number b=(Number) fenMu;
			if(b.doubleValue()==0||a.doubleValue()==0)
				return 0.00;
			return a.doubleValue()/b.doubleValue();
		}
		return 0.00;
	}

	public static String getDiv(Object fenZi, Object fenMu, boolean isPercent){
		if(!isPercent) return String.format("%.2f", getDiv(fenZi,fenMu));
		if(fenZi==null||fenMu==null)
			return "0.00%";
		if(fenZi instanceof Number && fenMu instanceof Number){
			Number a=(Number) fenZi;
			Number b=(Number) fenMu;
			if(b.doubleValue()==0||a.doubleValue()==0)
				return "0.00%";
			double res=(a.doubleValue()*100)/b.doubleValue();
			return String.format("%.2f", res)+"%";
		}
		return "0.00%";
	}

	public static double getAdd(Object a, Object b) {
		// TODO Auto-generated method stub
		if(a==null)a=0;
		if(b==null)b=0;
		if(a instanceof Number && b instanceof Number){
			return ((Number)a).doubleValue()+((Number)b).doubleValue();
		}
		return 0.00;
	}

    public static BigDecimal toFixed(Double num){
        if(num == null){
            num = 0d;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return new BigDecimal(df.format(num));
    }

	/**
	 * 单个对象的所有键值
	 * @param obj
	 * @return
     */
	public static Map<String, Object> getKeyAndValue(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		// 得到类对象
		Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
		Field[] fs = userCla.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true); // 设置些属性是可以访问的
			Object val ;
			try {
				val = f.get(obj);
				// 得到此属性的值
				map.put(f.getName(), val);// 设置键值
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static Map<String, String> getKeyAndType(Object obj){
		Map<String, String> map = new HashMap<String, String>();
		// 得到类对象
		Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
		Field[] fs = userCla.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			String typeName = f.getType().getName();
			map.put(f.getName(),typeName);
		}
		return map;
	}

	/**
	 * 比较两个对象的值是否不同
	 * @param obj1
	 * @param obj2
	 * @param props	需要比较的属性名称
     * @return
     */
	public static  boolean existsOnDiff(Object obj1, Object obj2, String[] props){
		boolean isDiff = false;
		if(obj1!=null&&obj2!=null){
			Map<String, Object> map1 = getKeyAndValue(obj1);
			Map<String, Object> map2 = getKeyAndValue(obj2);
			for(String key:props){
				Object v1 = map1.get(key);
				Object v2 = map2.get(key);
				if(v1!=null&&!v1.equals(v2)){
					isDiff = true;
					break;
				}
				if(v2!=null&&!v2.equals(v1)){
					isDiff = true;
					break;
				}
			}
		}
		return isDiff;
	}

    public static String join(List<String> list, String join, boolean isTrim){
        String str = "";
        if(list!=null&&list.size()>0){
            int len = list.size();
            for(int i=0;i<len;i++){
                String tempStr = isTrim ? StringUtils.trim(list.get(i)) : list.get(i);
                if(i==0){
                    str = tempStr;
                }else{
                    str +=join + tempStr;
                }
            }
        }
        return str;
    }

    /**
     * 将list转成带单引号连接的字符串，用于sql查询
     * @param list
     * @param join
     * @param isTrim
     * @return
     */
    public static String joinWithQuote(List<String> list, String join, boolean isTrim){
        String str = "";
        if(list!=null&&list.size()>0){
            int len = list.size();
            for(int i=0;i<len;i++){
                String tempStr = isTrim ? StringUtils.trim(list.get(i)) : list.get(i);
                tempStr = "'" + tempStr + "'";
                if(i==0){
                    str = tempStr;
                }else{
                    str +=join + tempStr;
                }
            }
        }
        return str;
    }

    public static String getUserStyle(String forward, String defaultStyle, String userStyle) {
    	String result = forward;
    	if(Utility.isNotEmpty(userStyle)) {
    		if(userStyle.trim().length() > 0) {
    			result = result+"_"+userStyle;
    		}
    	} else {
    		if(Utility.isNotEmpty(defaultStyle)) {
    			result = result+"_"+defaultStyle;
    		}
    	}
    	return result;
    }

}
