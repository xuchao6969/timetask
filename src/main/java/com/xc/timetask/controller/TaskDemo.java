package com.xc.timetask.controller;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TaskDemo {
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //注入需要的service
    //private ContractService contractService;

    public String run(){

        try {
            /*
            使用service做一些修改的业务操作
            获取所有的合同列表
            List<Contract> list = contractService.getAllContract();
            修改合同的状态
            contractService.updateContractList(list);

            下边用打印***来代替业务进行测试定时任务
             */
            System.out.println("*********");

            System.out.println("TaskDemo is running"+sdf.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/main";
    }
}