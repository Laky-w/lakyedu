package com.laky.edu.logistics.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRecord;

import java.util.LinkedHashMap;

public interface GoodsRecordService {

    GoodsRecord addRecord(GoodsRecord goodsRecord)throws Exception;

    PageBean<GoodsRecord> findRecordAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(GoodsRecord goodsRecord)throws Exception;

    int updateByPrimaryKey(GoodsRecord goodsRecord)throws Exception;
}