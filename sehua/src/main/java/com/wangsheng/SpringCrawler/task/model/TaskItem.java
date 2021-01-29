package com.wangsheng.SpringCrawler.task.model;

import com.wangsheng.SpringCrawler.model.ScanItemState;

public class TaskItem<T> {
    private ScanItemState Status = ScanItemState.NEW;
    private T content;
    private String id;
}
