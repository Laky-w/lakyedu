package com.laky.edu.organization.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.ImportRecord;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * Created by 湖之教育工作室·laky on 2018/3/17.
 */

public interface ImportService {

    /**
     * 查询导入记录
     * @param paramterMap
     * @return
     * @throws Exception
     */
   PageBean<ImportRecord> findImportRecord(LinkedHashMap paramterMap)throws Exception;
}
