package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface RoomDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Integer id);

    List<Room> selectByParameterMap(LinkedHashMap parameterMap);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
}