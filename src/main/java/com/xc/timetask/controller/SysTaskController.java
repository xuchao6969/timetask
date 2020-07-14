package com.xc.timetask.controller;


import com.xc.timetask.entity.Task;
import com.xc.timetask.service.TaskService;
import com.xc.timetask.util.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Lazy(value = false)
@Component
public class SysTaskController implements SchedulingConfigurer {

    protected static Logger logger = LoggerFactory.getLogger(SysTaskController.class);

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    private static Map<String,ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();

    @Resource
    private TaskService taskService;

    //从数据库里取得所有要执行的定时任务
    private List<Task> getAllTasks() throws Exception {
        List<Task> list=taskService.selectyunx();
        return list;
    }
    static {
        threadPoolTaskScheduler.initialize();
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<Task> tasks= null;
        try {
            tasks = getAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("定时任务启动,预计启动任务数量="+tasks.size()+"; time="+sdf.format(new Date()));

        //校验数据（这个步骤主要是为了打印日志，可以省略）
        checkDataList(tasks);

        //通过校验的数据执行定时任务
        int count = 0;
        if(tasks.size()>0) {
            for (Task task:tasks) {
                try {
                    //taskRegistrar.addTriggerTask(getRunnable(task), getTrigger(task));
                    start(task);
                    count++;
                } catch (Exception e) {
                    logger.error("定时任务启动错误:" + task.getBean_name() + ";" + task.getMethod_name() + ";" + e.getMessage());
                }
            }
        }
        logger.info("定时任务实际启动数量="+count+"; time="+sdf.format(new Date()));
    }
    private static Runnable getRunnable(Task task){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Object obj = SpringUtil.getBean(task.getBean_name());
                    Method method = obj.getClass().getMethod(task.getMethod_name());
                    method.invoke(obj);
                } catch (InvocationTargetException e) {
                    logger.error("定时任务启动错误，反射异常:"+task.getBean_name()+";"+task.getMethod_name()+";"+ e.getMessage());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        };
    }


    private static Trigger getTrigger(Task task){
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };

    }

    private List<Task> checkDataList(List<Task> list) {
        String errMsg="";
        for(int i=0;i<list.size();i++){
            if(!checkOneData(list.get(i)).equalsIgnoreCase("success")){
                errMsg+=list.get(i).getName()+";";
                list.remove(list.get(i));
                i--;
            };
        }
        if(!StringUtils.isBlank(errMsg)){
            errMsg="未启动的任务:"+errMsg;
            logger.error(errMsg);
        }
        return list;
    }

    public static String checkOneData(Task task){
        String result="success";
        Class cal= null;
        try {
            cal = Class.forName(task.getBean_name());

            Object obj = SpringUtil.getBean(cal);
            Method method = obj.getClass().getMethod(task.getMethod_name());
            String cron=task.getCron();
            if(StringUtils.isBlank(cron)){
                result="定时任务启动错误，无cron:"+task.getName();
                logger.error(result);
            }
        } catch (ClassNotFoundException e) {
            result="定时任务启动错误，找不到类:"+task.getBean_name()+ e.getMessage();
            logger.error(result);
        } catch (NoSuchMethodException e) {
            result="定时任务启动错误，找不到方法,方法必须是public:"+task.getBean_name()+";"+task.getMethod_name()+";"+ e.getMessage();
            logger.error(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
    /**
     * 启动定时任务
     * @param task
     * @param
     */
    public static void start(Task task){

        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(getRunnable(task),getTrigger(task));
        scheduledFutureMap.put(task.getId(),scheduledFuture);
        logger.info("启动定时任务" + task.getId() );

    }

    /**
     * 取消定时任务
     * @param task
     */
    public static void cancel(Task task){

        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(task.getId());

        if(scheduledFuture != null && !scheduledFuture.isCancelled()){
            scheduledFuture.cancel(Boolean.FALSE);
        }

        scheduledFutureMap.remove(task.getId());
        logger.info("取消定时任务" + task.getId() );

    }

    /**
     * 编辑
     * @param task
     * @param
     */
    public static void reset(Task task){
        logger.info("修改定时任务开始" + task.getId() );
        cancel(task);
        start(task);
        logger.info("修改定时任务结束" + task.getId());
    }

}

