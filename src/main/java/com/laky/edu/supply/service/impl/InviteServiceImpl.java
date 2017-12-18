package com.laky.edu.supply.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Invite;
import com.laky.edu.supply.dao.InviteDao;
import com.laky.edu.supply.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    private InviteDao inviteDao;

    /**
     * 添加邀约参观
     * @param invite
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Invite addInvite(Invite invite) throws Exception {
        invite.setTheStatus(1);
        if (invite.getInviteStatus()==null&&(invite.getInviteStatus()!=1 || invite.getInviteStatus()!=2) ){
            invite.setInviteStatus(1);
        }
        int rows = inviteDao.insert(invite);
        if (rows==0) throw new Exception("创建邀约试听失败");
        return invite;
    }

    /**
     * 查询全部邀约参观人支持分页查询
     * @param parameterMap
     * @return
     * @throws Exception
     */
    @Override
    public PageBean<Invite> findByInviteAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(inviteDao.selectByParameterMap(parameterMap));
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
