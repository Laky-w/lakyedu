package com.laky.edu.logistics.dao;
import com.laky.edu.logistics.bean.Goods;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface GoodsDao {

    /**
     * 增加物品
     * @param goods
     * @return
     */
    int insert(Goods goods);

    /**
     * 物品详情页
     * @param parameterMap
     * @return
     */
    Map selectGoodsById(LinkedHashMap parameterMap);

    /**
     * 查询物品
     * @param parameterMap
     * @return
     */
    List<Goods> selectByParameterMap(LinkedHashMap parameterMap);

    /**
     *修改物品
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    int updateByPrimaryKey(Goods record);
}