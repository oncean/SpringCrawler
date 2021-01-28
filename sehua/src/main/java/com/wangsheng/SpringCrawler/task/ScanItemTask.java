package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import lombok.extern.slf4j.Slf4j;
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
        try{
            List<Selectable> nodes = page.getHtml().css("div.pct td").nodes();
            if(nodes != null && nodes.size()>0){
                Selectable node =  nodes.get(0);
                Node item = Node.builder().url(page.getUrl().get())
                        .article(node.regex("【出演女优】：(.*?)<br>").get())
                        .imgList(node.css("img").regex("file=\"(.*?)\"").all())
                        .magnet(node.css("li").get())
                        .title(node.regex("【影片名称】：(.*?)<br>").get())
                        .status(1)
                        .build();
                result.setNode(item);
            }
        }catch (Exception e){
        }
    }

    @Override
    public List<String> buildUrls() {
        List<String> urls = new ArrayList<>();
        result.setStatus(Result.State.START_1);
        for (Node node : result.getNodes()){
            urls.add(node.getUrl());
        }
        return urls;
    }

    @Override
    public void end() {
        result.setStatus(Result.State.END);
    }
}
