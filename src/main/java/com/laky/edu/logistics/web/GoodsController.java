package com.laky.edu.logistics.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.bean.GoodsRecord;
import com.laky.edu.logistics.bean.GoodsRepository;
import com.laky.edu.logistics.service.GoodsRecordService;
import com.laky.edu.logistics.service.GoodsRepositoryService;
import com.laky.edu.logistics.service.GoodsService;
import com.laky.edu.organization.OrganizationConst;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
@RestController
@RequestMapping("/logistics")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/getGoodsList/{pageNum}/{pageSize}")
    public Map getGoodsList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(goodsService.findGoodsAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createGoods")
    public Map createGoods(HttpServletRequest request, Goods goods){
        try {
            goods.setBranchId(super.getCurrentUser(request).getBranchId());
            goodsService.addGoods(goods);
            super.handleOperate("添加物品管理", OrganizationConst.OPERATE_ADD,"物品名称【"+goods.getName()+"】",request);
            return super.doWrappingData(goods);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

//    ==================================
@Autowired
private GoodsRecordService goodsRecordService;

    @PostMapping("/getRecordList/{pageNum}/{pageSize}")
    public Map getRecordList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(goodsRecordService.findRecordAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createRecord")
    public Map createRecord(HttpServletRequest request, GoodsRecord goodsRecord){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            goodsRecord.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            goodsRecordService.addRecord(goodsRecord);
            super.handleOperate("添加库存记录", OrganizationConst.OPERATE_ADD,"添加库存记录人【"+ goodsRecord.getUserId()+"】,其他记录人:"+ goodsRecord.getOtherName(),request);
            return super.doWrappingData(goodsRecord);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

//=====================================

    @Autowired
    private GoodsRepositoryService goodsRepositoryService;

    @PostMapping("/getRepositoryList/{pageNum}/{pageSize}")
    public Map getRepositoryList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds", super.getSchoolIds(request));
            parameterMap.put("pageNum", pageNum);
            parameterMap.put("pageSize", pageSize);
            parameterMap = super.doWrappingFormParameter(request, parameterMap);
            return super.doWrappingData(goodsRepositoryService.findRepositoryAll(parameterMap));
        } catch (Exception e) {
            return super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createRepository")
    public Map createRepository(HttpServletRequest request, GoodsRepository goodsRepository) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            goodsRepository.setSchoolZoneId(super.getCurrentUser(request).getSchoolZoneId());
            goodsRepositoryService.addRepository(goodsRepository);
            super.handleOperate("添加库存", OrganizationConst.OPERATE_ADD, "添加库存物品【" + goodsRepository.getGoodsId() + "】,添加库存校区:" + goodsRepository.getSchoolZoneId(), request);
            return super.doWrappingData(goodsRepository);
        } catch (Exception e) {
            return super.doWrappingErrorData(e);
        }
    }

}
