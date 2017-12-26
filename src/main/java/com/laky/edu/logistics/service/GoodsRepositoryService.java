package com.laky.edu.logistics.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRepository;

import java.util.LinkedHashMap;

public interface GoodsRepositoryService {

    GoodsRepository addRepository(GoodsRepository goodsRepository)throws Exception;

    PageBean<GoodsRepository> findRepositoryAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(GoodsRepository record)throws Exception;

    int updateByPrimaryKey(GoodsRepository record)throws Exception;
}