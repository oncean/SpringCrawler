package com.wangsheng.SpringCrawler.service;

import com.github.pagehelper.PageInfo;
import com.wangsheng.SpringCrawler.entity.Node;

import java.util.List;

public interface NodeService {
    List<Node> getAllNodes();
    PageInfo<Node> getAllNodesForPage(int pageNo, int pageSize);
    boolean save(Node node);
    Node get(String code);
}
