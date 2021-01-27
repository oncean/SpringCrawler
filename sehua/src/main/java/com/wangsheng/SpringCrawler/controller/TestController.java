package com.wangsheng.SpringCrawler.controller;

import com.wangsheng.SpringCrawler.crawlers.SehuaCrawler;
import com.wangsheng.SpringCrawler.model.sehua.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @GetMapping("/test")
    public void test(){
        SehuaCrawler sehuaCrawler = new SehuaCrawler();
        List<Result> results = sehuaCrawler.getAll();
    }
}
