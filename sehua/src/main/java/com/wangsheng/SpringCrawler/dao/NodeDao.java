package com.wangsheng.SpringCrawler.dao;

import com.wangsheng.SpringCrawler.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeDao extends JpaRepository<Node,Integer> {
}
