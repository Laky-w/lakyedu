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

import java.util.*;

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
        if (rows==0) throw new RuntimeException("创建物品管理失败");
        return goods;
    }

    @Override
    public Map findGoodsById(LinkedHashMap parameterMap) throws Exception {
        return goodsDao.selectGoodsById(parameterMap);
    }

    @Override
    public PageBean<Goods> findGoodsAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(goodsDao.selectByParameterMap(parameterMap));
    }
    @Transactional
    @Override
    public int deleteGoods(Integer id) throws Exception {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setTheStatus(0);
        int rows = goodsDao.updateGoods(goods);
        if (rows==0) throw new RuntimeException("删除物品失败!");
        return rows;
    }

    @Override
    public Goods updateGoods(Goods goods) throws Exception {
        int rows = goodsDao.updateGoods(goods);
        if (rows==0) throw new RuntimeException("更新物品失败!");
        return goods;
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
        if(rowCount==0)throw  new RuntimeException("创建库存记录失败");
        List<GoodsRepository> insertGoodsRepositoryList = new ArrayList<>(); //插入库存
        List<GoodsRepository> updateGoodsRepositoryList = new ArrayList<>(); //更新库存
        for (GoodsRecord goodsRecord:goodsRecords){
            GoodsRepository repository ;
            if(goodsRecord.getTheType() == 1 ){  //1 进货
                repository = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId(),1,goodsRecord.getAmount());
            } else if (goodsRecord.getTheType() ==2 || goodsRecord.getTheType() ==3 || goodsRecord.getTheType() ==4 || goodsRecord.getTheType() ==5){ // 2 退货、3 销售、4 领用、5 图片借阅
                repository = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId(),2,goodsRecord.getAmount());
            } else  if(goodsRecord.getTheType() ==6){
                repository = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId(),2,goodsRecord.getAmount());//当前校区减少库存
                GoodsRepository repositoryIn = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneIdIn(),1,goodsRecord.getAmount()); //调拨校区增加库存
                if(repositoryIn.getId() ==null) {
                    insertGoodsRepositoryList.add(repositoryIn);
                } else {
                    updateGoodsRepositoryList.add(repositoryIn);
                }
            } else { //默认调整
                repository = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId(),3,goodsRecord.getAmount());
            }
            if(repository.getId() == null){
                insertGoodsRepositoryList.add(repository);
            } else {
                updateGoodsRepositoryList.add(repository);
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

    @Transactional
    @Override
    public GoodsRecord doReturnLibrary(GoodsRecord goodsRecord) throws Exception {
        //归还图书
        goodsRecord = goodsRecordDao.selectById(goodsRecord.getId());
        goodsRecord.setReturnStatus(2);
        goodsRecord.setReturnDate(new Date());
        int rowCount = goodsRecordDao.updateByPrimaryKeySelective(goodsRecord);
        if(rowCount ==0) {
            throw  new RuntimeException("归还图书失败");
        }
        GoodsRepository repository = getGoodsRepository(goodsRecord.getGoodsId(),goodsRecord.getSchoolZoneId(),1,goodsRecord.getAmount());
        goodsRepositoryDao.updateByPrimaryKeySelective(repository);
       // go
        //回归库存
        return goodsRecord;
    }

    private GoodsRepository getGoodsRepository(Integer goodsId,Integer schoolZoneId,int theType,Integer amount){
        GoodsRepository repository = goodsRepositoryDao.selectByPrimaryKey(goodsId,schoolZoneId);//查询物品库存通过物品id和学校id确定唯一性
        if(repository == null){
            repository = new GoodsRepository();
            repository.setSchoolZoneId(schoolZoneId);
            repository.setGoodsId(goodsId);
            repository.setConsumeAmount(0);
            repository.setLastAmount(0);
        }
        switch (theType){
            case 1: //增加库存
                repository.setLastAmount(repository.getLastAmount()+amount);
                break;
            case 2: //减少库存,增加消耗
                repository.setLastAmount(repository.getLastAmount()-amount);
                repository.setConsumeAmount(repository.getConsumeAmount() + amount);
                break;
            case 3:
                repository.setLastAmount(amount);
                break;
        }
        return repository;
    }

}
