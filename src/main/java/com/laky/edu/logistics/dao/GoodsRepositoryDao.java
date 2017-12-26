package com.laky.edu.logistics.dao;

import com.laky.edu.logistics.bean.GoodsRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface GoodsRepositoryDao {

    int insert(GoodsRepository goodsRepository);

    List<GoodsRepository> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(GoodsRepository record);

    int updateByPrimaryKey(GoodsRepository record);
}