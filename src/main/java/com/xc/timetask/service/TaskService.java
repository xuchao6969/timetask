package com.xc.timetask.service;

import com.xc.timetask.entity.Task;

import java.util.List;

public interface TaskService {
    //列表查询
    List<Task> selectByExample() throws Exception;
    //查询运行的列表
    List<Task> selectyunx() throws Exception;
    //根据id查询
    Task searchbyid(String id) throws Exception ;

    void add(Task task)throws Exception;

    void update(Task task)throws Exception;

    void updstatus(Task task)throws Exception;

    void deleteById(String id)throws Exception;
}
