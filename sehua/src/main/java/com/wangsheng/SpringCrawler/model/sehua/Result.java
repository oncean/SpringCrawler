package com.wangsheng.SpringCrawler.model.sehua;


import lombok.Data;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Data
public class Result{
    private String url;
    private String title;
    private String article;
    private List<String> imgList;
    private String magnet;
    private int status = 0;
    private Selectable node;
}