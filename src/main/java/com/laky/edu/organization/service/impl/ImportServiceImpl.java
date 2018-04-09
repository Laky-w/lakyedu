package com.laky.edu.organization.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.ImportRecord;
import com.laky.edu.organization.dao.ImportRecordDao;
import com.laky.edu.organization.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2018/3/17.
 */
@Service
public class ImportServiceImpl implements ImportService{

    @Autowired
    private ImportRecordDao importRecordDao;

    @Override
    public PageBean<ImportRecord> findImportRecord(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(importRecordDao.selectByParameterMap(parameterMap));
    }
}
