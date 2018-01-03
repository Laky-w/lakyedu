package com.laky.edu.logistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.bean.GoodsRecord;
import com.laky.edu.logistics.bean.GoodsRepository;
import com.laky.edu.logistics.dao.GoodsDao;
import com.laky.edu.logistics.dao.GoodsRecordDao;
import com.laky.edu.logistics.dao.GoodsRepositoryDao;
import com.laky.edu.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 湖之教育工作室·laky on 2017/12/29.
 */
@Service
public class LogisticsServiceImpl implements LogisticsService{

    @Autowired
    private GoodsRecordDao goodsRecordDao;

    @Autowired
    private GoodsRepositoryDao goodsRepositoryDao;

    @Autowired
    private GoodsDao goodsDao;

    @Transactional
    @Override
    public Goods createGoods(Goods goods) throws Exception {
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
    public PageBean<GoodsRepository> findRepositoryAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsRepositoryDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public List<GoodsRecord> createGoodsRecord(List<GoodsRecord> goodsRecords) throws Exception {
        int rowCount =goodsRecordDao.batchInsert(goodsRecords);
        if(rowCount==0)throw  new Exception("创建库存记录失败");
        //更新库存
        List<GoodsRepository> insertGoodsRepositoryList = new ArrayList<>();
        List<GoodsRepository> updateGoodsRepositoryList = new ArrayList<>();
        for (GoodsRecord goodsRecord:goodsRecords){
            GoodsRepository repository = goodsRepositoryDao.selectByPrimaryKey(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId());
            if(repository == null){
                repository = new GoodsRepository();
                repository.setSchoolZoneId(goodsRecord.getSchoolZoneId());
                repository.setGoodsId(goodsRecord.getGoodsId());
               // repository.setLastAmount(goodsRecord.getAmount().intValue()); //计算库存数量
                repository.setConsumeAmount(0);
                repository.setLastAmount(0);
                insertGoodsRepositoryList.add(repository);
            } else {

                updateGoodsRepositoryList.add(repository);
            }
            switch (goodsRecord.getTheType()){
                case 1: //进货
                    repository.setLastAmount(repository.getLastAmount()+goodsRecord.getAmount()); //计算库存数量
                    break;
                case 2: //退货
                    repository.setLastAmount(repository.getLastAmount()-goodsRecord.getAmount());
                    break;
                case 3://销售
                    repository.setConsumeAmount(goodsRecord.getAmount());
                    break;
                case 4://领用
                    repository.setLastAmount(repository.getLastAmount()-goodsRecord.getAmount());
                    break;
                case 5://图书借阅
//                    goodsRecord.setReturnStatus(1);
                    repository.setLastAmount(repository.getLastAmount()-goodsRecord.getAmount());
                    break;
                case 6://调拨
                    repository.setLastAmount(repository.getLastAmount()-goodsRecord.getAmount());
                    break;
                case 7://库存调整
                    repository.setLastAmount(repository.getLastAmount());
                break;

            }
        }
        if(insertGoodsRepositoryList.size()>0){
            goodsRepositoryDao.batchInsert(insertGoodsRepositoryList);
        }
        if(updateGoodsRepositoryList.size()>0){
            goodsRepositoryDao.batchUpdateByPrimaryKeySelective(updateGoodsRepositoryList);
        }
        return goodsRecords;
    }

    @Override
    public PageBean<GoodsRecord> findGoodsRecordAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsRecordDao.selectByParameterMap(parameterMap));
    }
}
