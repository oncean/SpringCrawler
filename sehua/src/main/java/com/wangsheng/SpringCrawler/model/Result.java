package com.wangsheng.SpringCrawler.model;


import lombok.Data;
import org.springframework.beans.BeanUtils;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Data
public class Result{
    public enum State {

        NEW,START_1,END_1,START_2,END
    }


    private List<Node> nodes = new ArrayList<>();

    private State status = State.NEW;

    private boolean errors = false;

    private long current;

    public void addNode(Node node){
        synchronized (nodes){
            nodes.add(node);
        }
    }

    public void setNode(Node node){
        for (Node item: nodes) {
            if(item.getUrl().equals(node.getUrl())){
                BeanUtils.copyProperties(node,item);
                node.setStatus(1);
                break;
            }
        }
    }
}
