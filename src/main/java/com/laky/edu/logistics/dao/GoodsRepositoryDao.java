package com.laky.edu.logistics.dao;

import com.laky.edu.logistics.bean.GoodsRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface GoodsRepositoryDao {

    int insert(GoodsRepository goodsRepository);

    int batchInsert(List<GoodsRepository> repositories);

    GoodsRepository selectByPrimaryKey(@Param("id") Integer id, @Param("schoolZoneId") Integer schoolZoneId);

    List<GoodsRepository> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(GoodsRepository record);

    int batchUpdateByPrimaryKeySelective(List<GoodsRepository> repositories);

    int updateByPrimaryKey(GoodsRepository record);
}