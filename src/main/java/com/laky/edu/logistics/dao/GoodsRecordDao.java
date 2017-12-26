package com.laky.edu.logistics.dao;

import com.laky.edu.logistics.bean.GoodsRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface GoodsRecordDao {

    int insert(GoodsRecord goodsRecord);

    List<GoodsRecord> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(GoodsRecord goodsRecord);

    int updateByPrimaryKey(GoodsRecord goodsRecord);
}