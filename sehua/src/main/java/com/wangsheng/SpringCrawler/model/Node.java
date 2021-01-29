package com.wangsheng.SpringCrawler.model;

import lombok.Data;

import java.util.List;

@Data
public class Node extends ScanItem {
    private String title;
    private String article;
    private List<String> imgList;
    private String magnet;
    private String code;

    public Node(String url){
        super(url);
    }
}


