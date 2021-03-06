package com.laky.edu.organization.service.impl;

import com.laky.edu.organization.bean.LakyTest;
import com.laky.edu.organization.dao.LakyTestDao;
import com.laky.edu.organization.service.LakyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/14.
 */
@Service
public class LakyTestServiceImpl implements LakyTestService {
    @Autowired
    private LakyTestDao lakyTestDao;
    @Override
    public List<LakyTest> findLakyTestAll() {
        return lakyTestDao.queryLakyTest();
    }

    @Override
    public List<LakyTest> findLakyTestAllByContitions(Integer sex) {
        LinkedHashMap paramentMap = new LinkedHashMap<>();
        paramentMap.put("sex",sex);
        return lakyTestDao.queryTestAllByContitions(paramentMap);
    }
}
