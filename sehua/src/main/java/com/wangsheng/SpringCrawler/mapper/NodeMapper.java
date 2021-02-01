package com.wangsheng.SpringCrawler.mapper;

import com.wangsheng.SpringCrawler.entity.Node;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NodeMapper {
    List<Node> getAllNodes();
}
