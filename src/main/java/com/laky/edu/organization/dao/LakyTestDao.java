package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.LakyTest;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */
@Component
public interface LakyTestDao {
    List<LakyTest>   queryLakyTest();

    List<LakyTest> queryTestAllByContitions(LinkedHashMap parameterMap);
}
