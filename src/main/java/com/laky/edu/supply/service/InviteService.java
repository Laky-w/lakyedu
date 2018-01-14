package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Invite;

import java.util.LinkedHashMap;
import java.util.List;

public interface InviteService {

    Invite addInvite(Invite invite)throws Exception;

    PageBean<Invite> findByInviteAll(LinkedHashMap parameterMap)throws Exception;

    int updateByPrimaryKeySelective(Invite record)throws Exception;

    int updateByPrimaryKey(Invite record)throws Exception;

    /**
     * 删除邀约记录
     * @param ids
     * @return
     * @throws Exception
     */
    int deleteInvite(String [] ids) throws Exception;
}