package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Activity;
import com.laky.edu.supply.dao.ActivityDao;
import com.laky.edu.supply.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private ActivityDao activityDao;

    @Transactional
    @Override
    public Activity createActivity(Activity activity) throws Exception {
        activity.setTheStatus(1);
        activity.setCreateTime(new Date());
        int rows=activityDao.insert(activity);
        if(rows==0) throw new Exception("创建市场活动失败！");
        return activity;
    }

    @Override
    public PageBean<Activity> findActivityAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(activityDao.selectByParameterMap(parameterMap));
    }

    @Override
    public Map queryActivity(LinkedHashMap parameterMap) throws Exception {
        return activityDao.selectActivity(parameterMap);
    }

}
