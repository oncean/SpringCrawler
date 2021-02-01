package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.entity.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.model.ScanItemState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScanItemTask extends AbstractTask{

    public ScanItemTask(Result result) {
        super(result);
    }

    @Override
    public void parse(Page page){
        Node item = new Node(page.getUrl().get());
        try{
            List<Selectable> nodes = page.getHtml().css("div.pct td").nodes();
            if(nodes != null && nodes.size()>0){
                Selectable node =  nodes.get(0);
                 item.setArticle(node.regex("【出演女优】：(.*?)<br>").get());
                 item.setCode(page.getHtml().css(".attnm a").regex(">(.*?)-C").get().toUpperCase());
                List<String> imgs = node.css("img").regex("file=\"(.*?)\"").all();
                if(imgs.size()> 0){
                    item.setCoverImg(imgs.get(0));
                }
                if(imgs.size()> 1){
                    item.setDetailImg(imgs.get(1));
                }
                item.setCreateTime(page.getHtml().css(".authi em span").regex("title=\"(.*?)\"").get());
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
            if(node.getState() == ScanItemState.NEW || node.getState() == ScanItemState.ERROR){
                urls.add(node.getUrl());
                node.setState(ScanItemState.NEW);
            }
        }
        return urls;
    }

    @Override
    public void onStart(Request request) {
        String url = request.getUrl();
        for (Node node : result.getNodes()){
            if(node.getUrl().equals(url)){
                node.setState(ScanItemState.LOADING);
            }
        }
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
                page.setState(ScanItemState.ERROR);
                break;
            }
        }
    }

    public void scanNodeSuccess(Node node){
        for (Node item: result.getNodes()) {
            if(item.getUrl().equals(node.getUrl())){
                BeanUtils.copyProperties(node,item);
                item.setState(ScanItemState.SUCCESS);
                break;
            }
        }
    }

    public void scanNodeError(Node node,String errMsg){
        for (Node item: result.getNodes()) {
            if(item.getUrl().equals(node.getUrl())){
                BeanUtils.copyProperties(node,item);
                node.setState(ScanItemState.ERROR);
                node.setErrMsg(errMsg);
                break;
            }
        }
    }
}
