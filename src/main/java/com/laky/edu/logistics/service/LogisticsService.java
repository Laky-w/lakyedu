package com.laky.edu.logistics.service;

import com.alibaba.fastjson.JSONArray;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.bean.GoodsRecord;
import com.laky.edu.logistics.bean.GoodsRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/29.
 */
public interface LogisticsService {
    /**
     * 创建商品
     * @param goods
     * @return
     * @throws Exception
     */
    Goods createGoods(Goods goods)throws Exception;

    /**
     *查询物品详情
     * @param parameterMap
     * @return
     */
    Map findGoodsById(LinkedHashMap parameterMap)throws Exception;
    /**
     * 查询商品
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Goods> findGoodsAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 删除物品
     * @param id
     * @return
     */
    int deleteGoods(Integer id)throws Exception;

    /**
     * 更新物品
     * @param goods
     * @return
     */
    Goods updateGoods(Goods goods)throws Exception;

    /**
     * 查看库存
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<GoodsRepository> findRepositoryAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 批量保存库存名称
     * @param goodsRecords
     * @return
     * @throws Exception
     */
    List<GoodsRecord> createGoodsRecord(List<GoodsRecord> goodsRecords)throws Exception;

    /**
     * 查询商品记录
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<GoodsRecord> findGoodsRecordAll(LinkedHashMap parameterMap)throws Exception;

    /**
     * 归还图书
     * @param goodsRecord
     * @return
     * @throws Exception
     */
    GoodsRecord doReturnLibrary(GoodsRecord goodsRecord)throws Exception;
}
