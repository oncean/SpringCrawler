package com.wangsheng.SpringCrawler.service;

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

    @Autowired
    private NodeDao nodeDao;

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

    public void saveToDb(String taskId){
        TotalTask totalTask = taskPool.get(taskId);

        if(totalTask==null||totalTask.getResult() == null) {
         return;
        }
        List<Node> wait =totalTask.getResult().getNodes();
        List<Node> nodes =nodeDao.findAll();
        List<Node> waitDb =new ArrayList<>();
        for (Node node :
                wait) {
            boolean flag = false;
            for (Node inner :
                    nodes) {
                if(node.getCode().equals(inner.getCode())){
                    flag= true;
                    break;
                }
            }
            if(!flag){
                waitDb.add(node);
            }
        }
            nodeDao.saveAll(waitDb);

    }
}
