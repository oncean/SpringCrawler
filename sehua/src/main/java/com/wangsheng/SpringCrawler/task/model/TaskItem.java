package com.wangsheng.SpringCrawler.task.model;

import com.wangsheng.SpringCrawler.model.TaskState;

public class TaskItem<T> {
    private TaskState Status = TaskState.LOADING;
    private T content;
    private String id;
}
