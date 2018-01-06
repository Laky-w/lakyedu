package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Activity;

import java.util.LinkedHashMap;
import java.util.Map;

public interface SupplyService {
    /**
     * 创建市场活动
     * @param activity
     * @return
     * @throws Exception
     */
    Activity createActivity(Activity activity)throws Exception;

    /**
     * 分页查询市场全部活动
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Activity> findActivityAll(LinkedHashMap parameterMap)throws Exception;


    /**
     * 查询市场活动详情页
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Map queryActivity(LinkedHashMap parameterMap)throws Exception;
}
