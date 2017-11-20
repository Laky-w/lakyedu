package com.laky.edu.organization.service;

import com.laky.edu.organization.bean.LakyTest;

import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */

public interface LakyTestService {
    /**
     * 查询全部测试数据
     * @return
     */
    List<LakyTest> findLakyTestAll();

    /**
     * 查询全部测试数据
     * @return
     */
    List<LakyTest> findLakyTestAllByContitions(Integer sex);
}
