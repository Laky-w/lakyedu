package com.laky.edu.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 95 on 2017/11/14.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    Map doWrappingData(Object object){
        Map map = new LinkedHashMap<>();
        map.put("code",200);
        map.put("message","成功");
        map.put("data",object);
        return map;
    }

    Map doWrappingErrorData(Exception e){
        logger.error("出错了",e);
        Map map = new LinkedHashMap<>();
        map.put("code",500);
        map.put("message","失败");
        map.put("data",e.getMessage());
        return map;
    }
}
