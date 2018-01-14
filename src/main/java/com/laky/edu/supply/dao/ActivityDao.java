package com.laky.edu.supply.dao;

import com.laky.edu.supply.bean.Activity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface ActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity activity);

    int insertSelective(Activity activity);

    Map selectActivity(LinkedHashMap parameterMap);

    List<Activity> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(List<Activity> activity);

    int updateByPrimaryKey(Activity activity);
}