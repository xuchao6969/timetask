package com.xc.timetask.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("task")
@Data
public class Task {

    private String id;
    private String name;        //任务名
    private String group_name; //任务组
    private String start_time; //开始执行时间
    private String end_time;    //结束执行时间
    private String cron;        //表达式
    private String job_status; //发布状态
    private String is_concurrent; //运行状态  1运行  0暂停
    private String job_data;    //参数
    private String bean_name;   //实例bean  全路径类名
    private String method_name; //方法  定时任务要执行的方法  Task里的run方法
    private String discription; //任务描述
    private String creat_user;  //创建者
    private String creat_time;  //创建时间
    private String update_time; //修改时间
    private String in_use;       //删除标志   1删除 0未删除

}
