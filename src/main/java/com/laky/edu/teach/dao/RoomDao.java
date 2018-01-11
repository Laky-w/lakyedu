package com.laky.edu.teach.dao;

import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.Room;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public interface RoomDao {
    int deleteRoom(Integer id);

    int insertRoom(Room room);

    int insertSelective(Room room);

    Map selectRoom(LinkedHashMap parameterMap);

    List<Room> selectByParameterMap(LinkedHashMap parameterMap);

    int updateRoom(Room room);

    int updateByPrimaryKey(Room record);
}