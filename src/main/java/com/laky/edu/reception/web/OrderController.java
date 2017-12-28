package com.laky.edu.reception.web;

import com.laky.edu.core.BaseController;
import com.laky.edu.reception.service.OrderService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/12/28.
 */
@RestController
@RequestMapping(value = "reception")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/getOrderList/{pageNum}/{pageSize}")
    public Map getOrderList(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolZoneId",super.getSchoolIds(request));
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(orderService.findStudentOrderAll(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getOrderDetailList/{orderId}")
    public Map getOrderDetailList(HttpServletRequest request, @PathVariable int orderId){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("orderId",orderId);
            return super.doWrappingData(orderService.findStudentOrderDetailByOrderId(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
