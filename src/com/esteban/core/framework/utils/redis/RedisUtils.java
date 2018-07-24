package com.esteban.core.framework.utils.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by CPR269 on 2018/6/14.
 */
@Service
public class RedisUtils {

    //配置文件中注入SpringRedisTemplate
    @Resource
    private RedisTemplate redisTemplate;

    /**
     *  删除key和value
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     *  根据key获取value
     */
    public Object getObject(String key){
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     *  根据key获取value
     */
    public String getString(String key){
        String value = redisTemplate.opsForValue().get(key)==null?"":redisTemplate.opsForValue().get(key).toString();
        return value;
    }


    /**
     *  将key和value存入redis，并设置有效时间，单位：天
     */
    public void set(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.DAYS);
    }

    /**
     *  将key和value存入redis
     */
    public void set(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *  从redis中获取map
     */
    public Map<String, Object> getMap(String key){
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        Map<String,Object> map = hash.entries(key);
        return map;
    }

    /**
     *  将map存入redis，并设置时效
     */
    public void set(String key, Map<? extends String, ? extends Object> map, long timeout){
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, timeout, TimeUnit.DAYS);
    }

}
