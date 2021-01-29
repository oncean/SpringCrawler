package com.wangsheng.SpringCrawler.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MainPage {
    private String url;
    private TaskState state = TaskState.LOADING;
    private List<Node> nodes;
    private String errMsg;

    public MainPage(String url){
        this.url = url;
    }
}
