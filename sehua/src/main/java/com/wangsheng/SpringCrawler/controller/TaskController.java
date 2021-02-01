package com.wangsheng.SpringCrawler.controller;


import com.wangsheng.SpringCrawler.dao.NodeDao;
import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.CreateParams;
import com.wangsheng.SpringCrawler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NodeDao nodeDao;

    @PostMapping("/create")
    public String createTask(@RequestBody CreateParams createParams){
        return taskService.create(createParams.getPages());
    }

    @GetMapping("/retry/{taskId}")
    public void retry(@PathVariable("taskId") String taskId){
        taskService.retry(taskId);
    }

    @GetMapping("/saveToDb/{taskId}")
    public void saveToDb(@PathVariable("taskId") String taskId){
        taskService.saveToDb(taskId);
    }


    @GetMapping("/test")
    public void test(){
        nodeDao.save(new Node());
    }


}
