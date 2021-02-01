package com.wangsheng.SpringCrawler.model;


import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.task.TotalTask;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Result{

    private List<MainPage> mainPageList = new ArrayList<>();

    private List<Node> nodes = new ArrayList<>();

    private TotalTask.State status = TotalTask.State.NEW;

    private long current;

}
