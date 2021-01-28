package com.wangsheng.SpringCrawler.model;

import lombok.Builder;
import lombok.Data;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Data
@Builder
public class Node {
    private String url;
    private String title;
    private String article;
    private List<String> imgList;
    private String magnet;
    private int status;
    private Selectable node;
}
