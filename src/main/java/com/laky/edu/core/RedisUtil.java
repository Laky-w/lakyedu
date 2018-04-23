package com.laky.edu.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis操作类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public  void pullData(String key,String value){
        this.pullData(key,value,30L);
    }

    public  void pullData(String key,String value,Long time){
        this.pullData(key,value,30L,TimeUnit.SECONDS);
    }

    public  void pullData(String key,String value,Long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);//默认分钟
    }

    public String getData(String key){
        Object object = redisTemplate.opsForValue().get(key);
        return object.toString();
    }

    public void delData(String key){
        redisTemplate.delete(key);
    }

    public RedisTemplate getRedisTemplate(){
        return this.redisTemplate;
    }
}
