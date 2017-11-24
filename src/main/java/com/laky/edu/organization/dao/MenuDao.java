package com.laky.edu.organization.dao;

import com.laky.edu.organization.bean.Menu;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
