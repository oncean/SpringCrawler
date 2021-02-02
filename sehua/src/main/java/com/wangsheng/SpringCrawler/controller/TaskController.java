package com.wangsheng.SpringCrawler.controller;


import com.github.pagehelper.PageInfo;
import com.wangsheng.SpringCrawler.dao.NodeDao;
import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.CreateParams;
import com.wangsheng.SpringCrawler.model.GetPageParams;
import com.wangsheng.SpringCrawler.service.NodeService;
import com.wangsheng.SpringCrawler.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/getByPage")
    @ResponseBody
    public PageInfo<Node> getByPage(@RequestBody GetPageParams pageParams){
        return taskService.getResultByPage(pageParams.getTaskId(),pageParams.getPageNo(),pageParams.getPageSize());
    }

}
