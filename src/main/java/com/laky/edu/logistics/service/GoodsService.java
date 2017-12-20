package com.laky.edu.logistics.service;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.Goods;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;


public interface GoodsService {

    Goods addGoods(Goods goods)throws Exception;

    PageBean<Goods> findGoodsAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(Goods record)throws Exception;

    int updateByPrimaryKey(Goods record)throws Exception;
}