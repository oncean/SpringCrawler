package com.wangsheng.SpringCrawler.service;

import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.task.TotalTask;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private static ConcurrentHashMap<String, TotalTask> taskPool = new ConcurrentHashMap<>();

    public String create(int total){
        TotalTask task = new TotalTask(total);
        task.start();
        taskPool.put(task.getTaskId(),task);
        return task.getTaskId();
    }

    public Result getResult(String taskId){
        return taskPool.get(taskId).getResult();
    }

    public Result retry(String taskId){
        TotalTask task = taskPool.get(taskId);
        task.interrupt();
    }
}
