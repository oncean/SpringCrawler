package com.wangsheng.SpringCrawler.dao;

import com.wangsheng.SpringCrawler.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeDao extends JpaRepository<Node,Integer> {

    List<Node> findByCode(String code);
}
