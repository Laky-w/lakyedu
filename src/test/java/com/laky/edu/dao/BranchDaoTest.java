package com.laky.edu.dao;

import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.dao.BranchDao;
import com.laky.edu.organization.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;


/**
 * Created by 95 on 2017/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BranchDaoTest {
    @Autowired
    private BranchDao branchDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsertBranch(){
        LinkedHashMap parament = new LinkedHashMap();
        parament.put("userName","admin");
        parament.put("serial","001");
        User user= userDao.queryUserByUserName(parament);
        System.out.println(user.getBranch().getId());
       /* Branch branch = new Branch();
        branch.setBranchName("测试机构");
        branch.setSerial("002");
        branch.setTheStatus(1);
        branch.setCreateDatetime(new Date());
        int rowIndex = branchDao.insertBranch(branch);
        assertEquals(1,rowIndex);*/
        //  assertEquals(2,rowIndex);
    }
}
