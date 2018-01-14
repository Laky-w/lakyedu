package com.laky.edu.supply.dao;

import com.laky.edu.supply.bean.Invite;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
@Component
public interface InviteDao {

    int insert(Invite invite);

    List<Invite> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(List<Invite> record);

    int updateByPrimaryKey(Invite record);
}