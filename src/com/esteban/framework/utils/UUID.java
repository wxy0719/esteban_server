package com.esteban.framework.utils;

public class UUID {
	public static String getUUID(){   
        String uuid = java.util.UUID.randomUUID().toString().trim().replaceAll("-", "");   
        return uuid;   
    }  
}
