package com.xc.timetask.dao;

import com.xc.timetask.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDao {
    List<Task> selectByExample();

    List<Task> selectyunx();

    Task searchbyid(String id);

    void insert(Task task);

    void update(Task task);

    void updstatus(Task task);

    void delete(String id);

}