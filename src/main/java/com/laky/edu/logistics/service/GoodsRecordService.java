package com.laky.edu.logistics.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRecord;

import java.util.LinkedHashMap;

public interface GoodsRecordService {


    GoodsRecord addRecord(GoodsRecord goodsRecord,String[] goodsId, JSONArray goodsList)throws Exception;

    PageBean<GoodsRecord> findRecordAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(GoodsRecord goodsRecord)throws Exception;

    int updateByPrimaryKey(GoodsRecord goodsRecord)throws Exception;
}