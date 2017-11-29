package com.laky.edu.dao;

import com.laky.edu.log.dao.OperateLogDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * Created by 95 on 2017/11/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OperateLogDaoTest {
    @Autowired
    private OperateLogDao operateLogDao;

    @Test
    public void testInsertBranch(){


       /*
        Operatelog operatelog = new Operatelog();
        operatelog.setAccountId(1);
        operatelog.setContent("订课");
        operatelog.setCreateTime(new Date());
        operatelog.setOperatePerson("wangdong");
        operatelog.setTheType(1);
        operatelog.setTitle("what");
        Branch branch =  new Branch();
        branch.setId(1);
        operatelog.setBranch(branch);
        operateLogDao.insertOperateLogDao(operatelog);
         */




     //   schoolZone.setCreateDatetime(new Date());
       // schoolZone.setSerial("001");
        //schoolZone.setPhone("010-110");
     /*
      SchoolZone schoolZone = new SchoolZone();
        schoolZone.setId(1);
        schoolZone.setName("总部1");
        schoolZone.setTheStatus(0);
        schoolZone.setTheType(2);
     Branch branch = new Branch();
        branch.setId(1);
        schoolZone.setBranch(branch);*/
        //int rowIndex =schoolZoneDao.updateSchoolZone(schoolZone);
        //assertEquals(1,rowIndex);
      //  SchoolZone schoolZone = schoolZoneDao.querySchoolZoneDaoById(1);
        //System.out.println("校区名称：" + schoolZone.getName());
    }
}
