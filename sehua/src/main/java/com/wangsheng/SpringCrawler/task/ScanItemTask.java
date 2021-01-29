package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.model.TaskState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ScanItemTask extends AbstartTask{
    public ScanItemTask(Result result,int maxRetryTime) {
        super(result,maxRetryTime);
    }

    @Override
    public void parse(Page page){
        Node item = new Node(page.getUrl().get());
        try{
            List<Selectable> nodes = page.getHtml().css("div.pct td").nodes();
            if(nodes != null && nodes.size()>0){
                Selectable node =  nodes.get(0);
                 item.setArticle(node.regex("【出演女优】：(.*?)<br>").get());
                 item.setCode(page.getHtml().$("#thread_subject").regex(">(.*?) ").get());
                 item.setImgList(node.css("img").regex("file=\"(.*?)\"").all());
                        item.setMagnet(node.css("li").regex("<li>(.*?)</li>").get());
                        item.setTitle(node.regex("【影片名称】：(.*?)<br>").get());
                scanNodeSuccess(item);
            }
        }catch (Exception e){
            log.error("parse page " + page.getUrl().get()+"failed,error is ", e);
            scanNodeError(item,e.getMessage());
        }
    }

    @Override
    public List<String> buildUrls() {
        List<String> urls = new ArrayList<>();
        result.setStatus(TotalTask.State.START_2);
        for (Node node : result.getNodes()){
            urls.add(node.getUrl());
        }
        return urls;
    }

    @Override
    public void end() {
        result.setStatus(TotalTask.State.END);
    }

    @Override
    public void errorHandler(String url) {
        for (Node page :
                result.getNodes()) {
            if (page.getUrl().equals(url)) {
                page.setState(TaskState.ERROR);
                break;
            }
        }
    }

    public void scanNodeSuccess(Node node){
        for (Node item: result.getNodes()) {
            if(item.getUrl().equals(node.getUrl())){
                BeanUtils.copyProperties(node,item);
                item.setState(TaskState.SUCCESS);
                break;
            }
        }
    }

    public void scanNodeError(Node node,String errMsg){
        for (Node item: result.getNodes()) {
            if(item.getUrl().equals(node.getUrl())){
                BeanUtils.copyProperties(node,item);
                node.setState(TaskState.ERROR);
                node.setErrMsg(errMsg);
                break;
            }
        }
    }
}
