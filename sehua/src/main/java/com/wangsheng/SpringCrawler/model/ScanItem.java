package com.wangsheng.SpringCrawler.model;

import lombok.Data;

@Data
public class ScanItem {
    private String url;
    private ScanItemState state = ScanItemState.NEW;
    private String errMsg;
    public ScanItem(){
    }

    public ScanItem(String url){
        this.url = url;
    }
}
