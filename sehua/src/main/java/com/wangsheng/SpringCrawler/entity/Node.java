package com.wangsheng.SpringCrawler.entity;

import com.wangsheng.SpringCrawler.model.ScanItem;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="t_nodes")
public class Node extends ScanItem {

    @Id    //主键id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String title;
    private String article;
    private String coverImg;
    private String detailImg;
    private String magnet;
    private String createTime;
    public Node(){

    }

    public Node(String url){
        super(url);
    }
}


