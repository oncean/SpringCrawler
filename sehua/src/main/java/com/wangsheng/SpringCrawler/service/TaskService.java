package com.wangsheng.SpringCrawler.service;

import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.PageInfo;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.task.TotalTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
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
        if(taskId == null){
            return null;
        }
        TotalTask  task = taskPool.get(taskId);
        if(task == null || task.getResult() == null){
            return null;
        }
        List<Node> nodes = task.getResult().getNodes();
        Collections.sort(nodes, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    Date d1 = df.parse(o1.getCreateTime());
                    Date d2 = df.parse(o2.getCreateTime());
                    return d1.getTime()<d2.getTime()?1:-1;
                }catch (Exception e){
                    log.error("sort nodes exception",e);
                    return 0;
                }
            }
        });
        return new PageInfo<Node>(task.getResult().getNodes(),pageNum,pageSize);
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
