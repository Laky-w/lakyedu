package com.laky.edu.config.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

@Configuration
@EnableCaching
public class CacheConfig {

//    @Bean
//    public CacheManager cacheManager(
//            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//        Customeriz cacheManager= new CustomizedRedisCacheManager(redisTemplate);
//        cacheManager.setDefaultExpiration(60);
//        Map<String,Long> expiresMap=new HashMap<>();
//        expiresMap.put("Product",5L);
//        cacheManager.setExpires(expiresMap);
//        return cacheManager;
//    }

    /**
     *
     * 使用Jackson2JsonRedisSerializer的方式序列化value值
     * 默认才有jdk(二进制)的方式序列化，被序列化需要实现Serializable接口。
     * @param connectionFactory
     * @return
     */
//    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);
//        使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
