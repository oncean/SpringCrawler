package com.wangsheng.SpringCrawler.controller;

import com.github.pagehelper.PageInfo;
import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.GetPageParams;
import com.wangsheng.SpringCrawler.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping("/save")
    @ResponseBody
    public void getByPage(@RequestBody Node node){
        nodeService.save(node);
    }

    @GetMapping("/ifExistInDB/{code}")
    @ResponseBody
    public boolean ifExistInDB(@PathVariable("code") String code){
        Node node = nodeService.get(code);
        return node != null;
    }
}
