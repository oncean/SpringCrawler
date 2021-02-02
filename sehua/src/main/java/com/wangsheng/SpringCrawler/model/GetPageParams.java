package com.wangsheng.SpringCrawler.model;

import lombok.Data;

@Data
public class GetPageParams {
    private String taskId;
    //从1开始
    private int pageNo = 1;
    private int pageSize = 10;
}
