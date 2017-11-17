package com.laky.edu.web;

import com.laky.edu.bean.LakyTest;
import com.laky.edu.service.LakyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 95 on 2017/11/14.
 */
@RestController
public class LakyTestController extends BaseController{
    @Autowired
    private LakyTestService lakyTestService;

    @GetMapping(value = "getLakyTestAll/{sex1}")
    public Map findLakyTestAll(@PathVariable(value = "sex1",required = false) Integer sex ){
        try {
            List<LakyTest>  dataList=lakyTestService.findLakyTestAllByContitions(sex);
           // throw new Exception("出错了");
            return  super.doWrappingData(dataList);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }

      /*  List reponseList = dataList;
        for (LakyTest lakyTest : dataList){
            if(sex!=null && sex.intValue() == lakyTest.getSex()){
                reponseList.remove(lakyTest);
            }
        }*/


    }
}
