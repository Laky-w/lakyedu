package com.laky.edu.logistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.dao.GoodsDao;
import com.laky.edu.logistics.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public Goods addGoods(Goods goods) throws Exception {
        goods.setTheStatus(1);
        goods.setCreateTime(new Date());
        goods.setTheType(1);
        int rows = goodsDao.insert(goods);
        if (rows==0) throw new Exception("创建物品管理失败");
        return goods;
    }

    @Override
    public PageBean<Goods> findGoodsAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(Goods record) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Goods record) throws Exception {
        return 0;
    }
}
