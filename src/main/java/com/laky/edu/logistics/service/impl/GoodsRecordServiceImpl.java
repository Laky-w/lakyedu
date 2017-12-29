package com.laky.edu.logistics.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.GoodsRecord;
import com.laky.edu.logistics.dao.GoodsRecordDao;
import com.laky.edu.logistics.service.GoodsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Transactional
    @Override
    public GoodsRecord addRecord(GoodsRecord goodsRecord,String[] goodsId, JSONArray goodsList) throws Exception {

        goodsRecord.setTheType(1);
        goodsRecord.setcreateTime(new Date());
        int rows = goodsRecordDao.insert(goodsRecord);
        if (rows==0)throw new Exception("创建库存记录失败");
//      出入库保存
        if (goodsList!=null && goodsList.size()>0){
            List<Map> goodsRecordList = new ArrayList<>();
            for (Object object: goodsList
                 ) {Map objectMap = (Map) object;
                 objectMap.put("goodsId",goodsRecord.getGoodsId());
                 objectMap.put("theType",1);
                 goodsRecordList.add(objectMap);
            }
             goodsRecordDao.insertGoodsRecord(goodsRecordList);
        }
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
