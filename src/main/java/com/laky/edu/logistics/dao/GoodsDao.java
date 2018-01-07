package com.laky.edu.logistics.dao;
import com.laky.edu.logistics.bean.Goods;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface GoodsDao {

    int insert(Goods goods);

    Map selectGoodsById(LinkedHashMap parameterMap);

    List<Goods> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}