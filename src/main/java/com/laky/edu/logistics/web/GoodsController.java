package com.laky.edu.logistics.web;

import com.alibaba.fastjson.JSON;
import com.laky.edu.core.BaseController;
import com.laky.edu.logistics.web.form.GoodsRecordForm;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.bean.GoodsRecord;;
import com.laky.edu.logistics.service.LogisticsService;
import com.laky.edu.organization.OrganizationConst;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/logistics")
public class GoodsController extends BaseController {

    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/getGoodsList/{pageNum}/{pageSize}")
    public Map getGoodsList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("branchId",super.getCurrentUser(request).getBranchId());
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(logisticsService.findGoodsAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createGoods")
    public Map createGoods(HttpServletRequest request, Goods goods){
        try {
            goods.setBranchId(super.getCurrentUser(request).getBranchId());
            logisticsService.createGoods(goods);
            super.handleOperate("添加物品管理", OrganizationConst.OPERATE_ADD,"物品名称【"+goods.getName()+"】",request);
            return super.doWrappingData(goods);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getRecordList/{pageNum}/{pageSize}")
    public Map getRecordList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(logisticsService.findGoodsRecordAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/createGoodsRecord")
    public Map createGoodsRecord(HttpServletRequest request, String goodsRecordJson){
        try {
            GoodsRecordForm recordForm = JSON.parseObject(goodsRecordJson,GoodsRecordForm.class);
            List<GoodsRecord> goodsRecordList= recordForm.getGoodsList();
            for (GoodsRecord goodsRecord : goodsRecordList){
                goodsRecord.setUserId(getCurrentUser(request).getId());
                goodsRecord.setCreateTime(recordForm.getCreateTime());
                goodsRecord.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
                goodsRecord.setSupplierId(recordForm.getSupplierId());
            }
            goodsRecordList = logisticsService.createGoodsRecord(goodsRecordList);
            super.handleOperate("添加库存记录", OrganizationConst.OPERATE_ADD,"库存列表...",request);
            return super.doWrappingData(goodsRecordList);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getRepositoryList/{pageNum}/{pageSize}")
    public Map getRepositoryList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize) {
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId", super.getSchoolIds(request));
            parameterMap.put("pageNum", pageNum);
            parameterMap.put("pageSize", pageSize);
            parameterMap = super.doWrappingFormParameter(request, parameterMap);
            return super.doWrappingData(logisticsService.findRepositoryAll(parameterMap));
        } catch (Exception e) {
            return super.doWrappingErrorData(e);
        }
    }

}
