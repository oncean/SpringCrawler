package com.wangsheng.SpringCrawler.service;

import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.task.TotalTask;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private static ConcurrentHashMap<String, TotalTask> taskPool = new ConcurrentHashMap<>();

    public String create(int []pages){
        TotalTask task = new TotalTask(pages);
        task.start();
        taskPool.put(task.getTaskId(),task);
        return task.getTaskId();
    }

    public Result getResult(String taskId){
        TotalTask  task = taskPool.get(taskId);
        if(task == null){
            return null;
        }
        return task.getResult();
    }

    public void retry(String taskId){
        TotalTask task = taskPool.get(taskId);
        if(task == null){
            return;
        }
        if(!task.isAlive()){
            task.start();
        }
    }
}
