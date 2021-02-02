package com.wangsheng.SpringCrawler.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangsheng.SpringCrawler.dao.NodeDao;
import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.task.TotalTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private static ConcurrentHashMap<String, TotalTask> taskPool = new ConcurrentHashMap<>();

    public String create(int ...pages){
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

    public PageInfo<Node> getResultByPage(String taskId,int pageNum,int pageSize){
        TotalTask  task = taskPool.get(taskId);
        if(task == null || task.getResult() == null){
            return null;
        }
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(task.getResult().getNodes());
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
