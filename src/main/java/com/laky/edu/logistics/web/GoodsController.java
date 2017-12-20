package com.laky.edu.logistics.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.logistics.bean.Goods;
import com.laky.edu.logistics.service.GoodsService;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.supply.bean.Invite;
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
            super.handleOperate("添加物品管理", OrganizationConst.OPERATE_ADD,"添加邀约参观,参观人【"+goods.getName()+"】",request);
            return super.doWrappingData(goods);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

}
