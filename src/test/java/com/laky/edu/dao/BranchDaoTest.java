package com.laky.edu.dao;

import com.laky.edu.bean.Branch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * Created by 95 on 2017/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BranchDaoTest {
    @Autowired
    private BranchDao branchDao;

    @Test
    public void testInsertBranch(){
        Branch branch = new Branch();
        branch.setBranchName("测试机构");
        branch.setSerial("002");
        branch.setTheStatus(1);
        branch.setCreateDatetime(new Date());
        int rowIndex = branchDao.insertBranch(branch);
        assertEquals(1,rowIndex);
        //  assertEquals(2,rowIndex);
    }
}
