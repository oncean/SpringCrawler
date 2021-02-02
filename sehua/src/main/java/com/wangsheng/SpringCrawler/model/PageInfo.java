package com.wangsheng.SpringCrawler.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfo<T> {
    private int total;
    private List<T> result = new ArrayList<>();

    //从1开始
    public PageInfo(List<T> all,int pageNo,int pageSize){
        if(all == null){
            this.total = 0;
            return;
        }
        this.total = all.size();
        int offset = (pageNo-1)*pageSize;
        for (int i=0;i<pageSize;i++){
            if(offset+i<total){
                result.add(all.get(offset+i));
            }
        }
    }
}
