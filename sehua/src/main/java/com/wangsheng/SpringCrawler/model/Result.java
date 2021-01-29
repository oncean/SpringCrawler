package com.wangsheng.SpringCrawler.model;


import com.wangsheng.SpringCrawler.task.TotalTask;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Result{

    private List<MainPage> mainPageList = new ArrayList<>();

    private List<Node> nodes = new ArrayList<>();

    private TotalTask.State status = TotalTask.State.NEW;

    private boolean errors = false;

    private long current;

}
