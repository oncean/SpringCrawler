package com.ws.task.model;


import lombok.Data;
import org.springframework.beans.BeanUtils;
import us.codecraft.webmagic.Page;

import java.beans.beancontext.BeanContext;

@Data
public class CrawlerItem<T> {
    public enum Status{
        NEW,WORKING,END,ERROR
    }
    private Status status = Status.NEW;

    private T content;

    private String url;



    /**
     * 流程相关
     */
    public void onStart(){
        this.status = Status.WORKING;
    }
    public void onSuccess(CrawlerItem item){
        BeanUtils.copyProperties(item,this);
        this.status = Status.END;
    }
    public void onError(){
        this.status = Status.ERROR;
    }

}
