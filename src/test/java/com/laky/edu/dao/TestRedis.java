package com.laky.edu.dao;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.PageBean;
import com.laky.edu.finance.service.FinanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FinanceService financeService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        LinkedHashMap queryMap = new LinkedHashMap();
        queryMap.put("pageNum",1);
        queryMap.put("pageSize",20);

        List schoolZoneId = new ArrayList();
        schoolZoneId.add(1);
        queryMap.put("schoolZoneId",schoolZoneId);
        queryMap.put("branchId","1");

        try {
           Set set =  redisTemplate.keys("financeAccountList:1");
           for(Object object:set){
               redisTemplate.delete(object);
               System.out.println(object);
               System.out.println(JSON.toJSONString(redisTemplate.opsForValue().get(object)));
           }


//           PageBean list = financeService.findFinanceAccountAllBySchool(queryMap);
//           System.out.println(JSON.toJSONString(list));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
