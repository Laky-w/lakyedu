package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Menu;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by 湖之教育工作室·laky on 2017/11/24.
 */
@Component
public interface MenuDao {
    /**
     * 查询全部菜单
     * @return
     */
    List<Menu> findMenuAll();

    /**
     * 查询全部菜单
     * @return
     */
    List<Menu> findMenuAllByIds(LinkedHashMap parameterMap);
}
