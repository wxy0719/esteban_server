package com.esteban.core.framework.utils;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

	// 从一个对象设置非空属性导另一个对象
	public static <T> void copyNotNullField(T source, T target) {
		try {
			for (Field f : source.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				for(Field t : target.getClass().getDeclaredFields()){
					t.setAccessible(true);
					if (!"id".equals(f.getName())&&f.getName().equals(t.getName())&& f.get(source) != null) { // 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
						Method m = target.getClass().getMethod(
								"set"
										+ f.getName().substring(0, 1).toUpperCase()
										+ f.getName().substring(1,
												f.getName().length()), f.getType());
						m.invoke(target, f.get(source));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 对象非空字段设置到map
	public static <T> Map<String, Object> notNullFieldToMap(T source) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (Field f : source.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (!"id".equals(f.getName()) && f.get(source) != null&&StringUtils.isNotBlank(f.get(source).toString())) { // 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					map.put(f.getName(), f.get(source));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 处理主键之外的元素
	 * @param target
	 * @param map
	 */
	public static <T> void populateMapToBean(T target, Map<String,String> map) {
		
		try {
			for (Field f : target.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				String name = f.getName();
				if (!"id".equals(name)&&map.containsKey(name)) {
					Method m = target.getClass().getMethod(
							"set"
									+ f.getName().substring(0, 1).toUpperCase()
									+ f.getName().substring(1,
											f.getName().length()), f.getType());
					
					// 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					String val = map.get(name);
					String type = f.getGenericType().toString();
					if(type.endsWith("Boolean")){
						m.invoke(target, TypeUtils.castToBoolean(val));
					}else if(type.endsWith("Byte")){
						m.invoke(target, TypeUtils.castToByte(val));
					}else if(type.endsWith("Short")){
						m.invoke(target, TypeUtils.castToShort(val));
					}else if(type.endsWith("Integer")){
						m.invoke(target, TypeUtils.castToInt(val));
					}else if(type.endsWith("Long")){
						m.invoke(target, TypeUtils.castToLong(val));
					}else if(type.endsWith("Float")){
						m.invoke(target, TypeUtils.castToFloat(val));
					}else if(type.endsWith("Double")){
						m.invoke(target, TypeUtils.castToDouble(val));
					}else if(type.endsWith("BigDecimal")){
						m.invoke(target, TypeUtils.castToBigDecimal(val));
					}else if(type.endsWith("Timestamp")){
						if(StringUtils.isEmpty(val)){
							m.invoke(target, (Object)null);
						}else{
							if(val.length()<11){
								m.invoke(target, DateOperator.getDate(val, DateOperator.DATE_TIME_DISPLAY));
							}else{
								m.invoke(target, DateOperator.getDate(val, DateOperator.DATE_TIME_DISPLAY));
							}
						}
						
					}else if(type.endsWith("Date")){
						if(StringUtils.isEmpty(val)){
							m.invoke(target, (Object)null);
						}else{
							m.invoke(target, DateOperator.getDate(val, DateOperator.DATE_TIME_DISPLAY));
						}
					}else if(type.endsWith("String")){
						m.invoke(target, TypeUtils.castToString(val));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		System.out.println(Timestamp.valueOf("2016-05-06 00:00:00"));
		
		
	}
	
}
