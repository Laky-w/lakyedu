package com.laky.edu.logistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRepository;
import com.laky.edu.logistics.dao.GoodsRepositoryDao;
import com.laky.edu.logistics.service.GoodsRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
@Service
public class GoodsRepositoryServiceImpl implements GoodsRepositoryService {

    @Autowired
    private GoodsRepositoryDao goodsRepositoryDao;

    /**
     * 添加一个库存
     * @param goodsRepository
     * @return
     * @throws Exception
     */
    @Override
    public GoodsRepository addRepository(GoodsRepository goodsRepository) throws Exception {
        int rows = goodsRepositoryDao.insert(goodsRepository);
        if (rows==0)throw new Exception("创建库存失败");
        return goodsRepository;
    }

    /**
     * 查询库存和分页
     * @param parameterMap
     * @return
     * @throws Exception
     */
    @Override
    public PageBean<GoodsRepository> findRepositoryAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsRepositoryDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(GoodsRepository record) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(GoodsRepository record) throws Exception {
        return 0;
    }
}
