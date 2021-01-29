package com.wangsheng.SpringCrawler.model;

import lombok.Data;

@Data
public class MainPage extends ScanItem{
    private int nodeNum = 0;

    public MainPage(String url){
        super(url);
    }
}
