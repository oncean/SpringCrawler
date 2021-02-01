package com.wangsheng.SpringCrawler.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.mapper.NodeMapper;
import com.wangsheng.SpringCrawler.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;


    @Override
    public List<Node> getAllNodes() {
        return nodeMapper.getAllNodes();
    }

    @Override
    public PageInfo<Node> getAllNodesForPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Node> all = nodeMapper.getAllNodes();
        PageInfo<Node> pageInfo = new PageInfo<>(all);
        return pageInfo;
    }
}
