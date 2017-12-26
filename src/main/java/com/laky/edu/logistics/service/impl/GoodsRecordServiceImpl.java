package com.laky.edu.logistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRecord;
import com.laky.edu.logistics.dao.GoodsRecordDao;
import com.laky.edu.logistics.service.GoodsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class GoodsRecordServiceImpl implements GoodsRecordService {

    @Autowired
    private GoodsRecordDao goodsRecordDao;

    /**
     * 添加库存记录
     * @param goodsRecord
     * @return
     * @throws Exception
     */
    @Override
    public GoodsRecord addRecord(GoodsRecord goodsRecord) throws Exception {
        goodsRecord.setTheType(1);
        goodsRecord.setcreateTime(new Date());
        int rows = goodsRecordDao.insert(goodsRecord);
        if (rows==0)throw new Exception("创建库存记录失败");
        return goodsRecord;
    }

    /**
     * 添加查询记录和分页
     * @param parameterMap
     * @return
     * @throws Exception
     */
    @Override
    public PageBean<GoodsRecord> findRecordAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsRecordDao.selectByParameterMap(parameterMap));
    }

    @Override
    public int updateByPrimaryKeySelective(GoodsRecord goodsRecord) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(GoodsRecord goodsRecord) throws Exception {
        return 0;
    }
}
