package com.xc.timetask.controller;


import com.xc.timetask.entity.Task;
import com.xc.timetask.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/timetask")
public class TimeTaskController {
    private final String VIEW_PATH = "/time";
    @Resource
    private TaskService taskService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, HttpServletRequest request) {
        try {
            List<Task> list = taskService.selectByExample();
            modelMap.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW_PATH + "/list";
    }

    /**
     * 跳转到定时新增 编辑 页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/addUI")
    public String addUI(ModelMap modelMap,String id) throws Exception{
        if(id!=null){
            Task task = taskService.searchbyid(id);
            modelMap.addAttribute("task",task);
        }else {
            modelMap.addAttribute("task",new Task());
        }
        return VIEW_PATH + "/add";
    }
    /**
     * 新增定时任务
     * @param task
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object addSupplier(Task task){
        try {

            task.setCreat_user("aaa");
            taskService.add(task);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 编辑定时
     * @param task
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Object editSupplier(Task task){
        try {
            taskService.update(task);
            if(task.getJob_status()=="1"){
                SysTaskController.reset(task);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 删除定时
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public Object deleteById(String id,Task task){
        try {
            taskService.deleteById(id);
            Task tasks = taskService.searchbyid(id);
            if(tasks.getJob_status()=="1"){
                SysTaskController.cancel(task);
                task.setJob_status("0");
                taskService.updstatus(task);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    /**
     * 修改状态
     * @param id
     * @param is_concurrent
     * @return
     */
    @RequestMapping("/changeStatus")
    @ResponseBody
    public String changeState(String id, String  is_concurrent, Task task){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            task.setIs_concurrent(is_concurrent);
            task.setId(id);
            if(is_concurrent.equals("1")){
                Task tasks = taskService.searchbyid(id);
                if(!SysTaskController.checkOneData(tasks).equalsIgnoreCase("success")){
                    return "fial";
                }else {
                    SysTaskController.start(tasks);
                    task.setStart_time(df.format(new Date()));
                    taskService.updstatus(task);
                }
            }else {
                SysTaskController.cancel(task);
                task.setEnd_time(df.format(new Date()));
                taskService.updstatus(task);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
