package com.laky.edu.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.laky.edu.core.PageBean;
import com.laky.edu.dao.BranchDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 95 on 2017/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BranchServiceTest {
    @Autowired
    private OrganizationService organizationService;
    @Test
    public void testFindBranchAll(){
        try {
            PageBean list =organizationService.findBranchAll(2,40);
            //Assert.
            System.out.println(list.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
