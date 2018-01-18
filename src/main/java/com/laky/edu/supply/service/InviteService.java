package com.laky.edu.supply.service;

import com.laky.edu.core.PageBean;
import com.laky.edu.supply.bean.Invite;

import java.util.LinkedHashMap;
import java.util.List;

public interface InviteService {

    /**
     * 添加邀约参观
     * @param invite
     * @return
     * @throws Exception
     */
    Invite addInvite(Invite invite)throws Exception;

    /**
     * 修改试听
     * @param invite
     * @return
     * @throws Exception
     */
    Invite updateInvite(Invite invite)throws Exception;

    /**
     * 查询邀约详情
     * @param parameterMap
     * @return
     * @throws Exception
     */
    Invite findInviteById(LinkedHashMap parameterMap)throws Exception;

    /**
     * 查询全部邀约参观人支持分页查询
     * @param parameterMap
     * @return
     * @throws Exception
     */
    PageBean<Invite> findInviteAll(LinkedHashMap parameterMap)throws Exception;

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