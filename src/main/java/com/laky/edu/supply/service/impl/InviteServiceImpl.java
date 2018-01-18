package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.bean.Invite;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.supply.dao.InviteDao;
import com.laky.edu.supply.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    private InviteDao inviteDao;
    @Autowired
    private CustomerDao customerDao;


    @Transactional
    @Override
    public Invite addInvite(Invite invite) throws Exception {

        invite.setTheStatus(1);
        int rows = inviteDao.insert(invite);
        if (rows==0) throw new RuntimeException("创建邀约参观失败");
        Customer customer= customerDao.selectByPrimaryKey(invite.getStudentId());
        customer.setInviteStatus(2);
        customer.setContactStatus(2);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerDao.updateByPrimaryKeySelective(customerList);
        return invite;
    }

    @Transactional
    @Override
    public Invite updateInvite(Invite invite) throws Exception {
        List<Invite> inviteList = new ArrayList<>();
        inviteList.add(invite);
        int rows = inviteDao.updateByPrimaryKeySelective(inviteList);
        if (rows==0) throw new RuntimeException("修改邀约参观失败");
        return invite;
    }

    @Override
    public Invite findInviteById(LinkedHashMap parameterMap) throws Exception {
        return inviteDao.selectById(parameterMap);
    }

    @Override
    public PageBean<Invite> findInviteAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(inviteDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public int deleteInvite(String[] ids) throws Exception {
        List<Invite> inviteList = new ArrayList<>();
        for(String id:ids){
            Invite invite = new Invite();
            invite.setTheStatus(0);
            invite.setId(new Integer(id));
            inviteList.add(invite);
        }
        return inviteDao.updateByPrimaryKeySelective(inviteList);
    }

    @Override
    public int updateByPrimaryKeySelective(Invite record) throws Exception {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Invite record) throws Exception {
        return 0;
    }
}
