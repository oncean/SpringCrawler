package com.wangsheng.SpringCrawler.controller;


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

    @PostMapping("/create")
    public String createTask(@RequestBody CreateParams createParams){
        return taskService.create(createParams.getPages());
    }

    @GetMapping("/retry/{taskId}")
    public void retry(@PathVariable("taskId") String taskId){
        taskService.retry(taskId);
    }
}
