package com.laky.edu.logistics.dao;

import com.laky.edu.logistics.bean.GoodsRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface GoodsRecordDao {



    int insert(GoodsRecord goodsRecord);

    int batchInsert(List<GoodsRecord> goodsRecordList);

    GoodsRecord selectById(Integer id);

    List<GoodsRecord> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(GoodsRecord goodsRecord);

    int updateByPrimaryKey(GoodsRecord goodsRecord);
}