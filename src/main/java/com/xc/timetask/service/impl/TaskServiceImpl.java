package com.xc.timetask.service.impl;


import com.xc.timetask.dao.TaskDao;
import com.xc.timetask.entity.Task;
import com.xc.timetask.service.TaskService;
import com.xc.timetask.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskDao dao;


    @Override
    public List<Task> selectByExample() throws Exception {
        return dao.selectByExample();
    }

    @Override
    public List<Task> selectyunx() throws Exception {
        return dao.selectyunx();
    }

    @Override
    public Task searchbyid(String id) throws Exception {
        Task t = dao.searchbyid(id);
        return t;
    }

    @Override
    public void add(Task task) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setCreat_time(df.format(new Date()));
        task.setIs_concurrent("0");
        task.setIn_use("0");
        task.setId(UuidUtil.get32UUID());
        dao.insert(task);
    }

    @Override
    public void update(Task task) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setUpdate_time(df.format(new Date()));
        dao.update(task);
    }

    @Override
    public void updstatus(Task task) throws Exception {
        dao.updstatus(task);
    }

    @Override
    public void deleteById(String id) throws Exception {
        dao.delete(id);
    }
}
