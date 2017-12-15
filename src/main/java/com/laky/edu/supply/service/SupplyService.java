package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Activity;

import java.util.LinkedHashMap;

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


}
