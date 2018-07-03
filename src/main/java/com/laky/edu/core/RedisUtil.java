package com.laky.edu.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
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
        this.pullData(key,value,time,TimeUnit.MINUTES);
    }

    public  void pullData(String key,String value,Long time,TimeUnit timeUnit){
        //默认分钟
        redisTemplate.opsForValue().set(key,value,time, timeUnit);
    }

    public String getData(String key){
        Object object = redisTemplate.opsForValue().get(key);
        return object==null?null:object.toString();
    }

    public Set getSetData(String key){
        Set set = redisTemplate.opsForSet().members(key);
        return set;
    }

    public Set addSetData(String key,Object value){
        redisTemplate.opsForSet().add(key,value);
        return this.getSetData(key);
    }

    public Set delSetData(String key,Object value){
        redisTemplate.opsForSet().remove(key,value);
        return this.getSetData(key);
    }

    public void delData(String key){
        redisTemplate.delete(key);
    }

    public RedisTemplate getRedisTemplate(){
        return this.redisTemplate;
    }
}
