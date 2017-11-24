package com.laky.edu.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 95 on 2017/11/14.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    public static Map userSession = new HashMap<>();
    public Map doWrappingData(Object object){
        Map map = new LinkedHashMap<>();
        map.put("code",200);
        map.put("message","成功");
        map.put("data",object);
        return map;
    }

    public String createToken(){
        String token = UUID.randomUUID ().toString ().replace ("-", "");
        return token;
    }

    public Map doWrappingErrorData(Exception e){
        logger.error("出错了",e);
        Map map = new LinkedHashMap<>();
        map.put("code",500);
        map.put("message","失败");
        map.put("data",e.getMessage());
        return map;
    }

    public String getRemortIP(HttpServletRequest request) {

        if (request.getHeader("x-forwarded-for") == null) {

            return request.getRemoteAddr();

        }

        return request.getHeader("x-forwarded-for");

    }
}
