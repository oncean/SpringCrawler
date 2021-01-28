package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import lombok.extern.slf4j.Slf4j;
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
public class ScanPageTask extends AbstartTask {
    private int total;
    private String LIST_PAGE = "https://sehuatang.org/forum-103-{0}.html";

    public ScanPageTask(Result result,int total) {
        super(result);
        this.total = total;
    }


    @Override
    public void parse(Page page){
        try{
            List<Selectable> nodes = page.getHtml().css("th.new").nodes();
            nodes.addAll(page.getHtml().css("th.common").nodes());
            if(nodes != null && nodes.size()>0){
                for (Selectable node :nodes
                ) {
                    if(node.regex("隐藏置顶帖").get() == null){
                        String url = node.links().regex("https://sehuatang.org/(.*?).html",0).get();
                        Node item = Node.builder().url(url).build();
                        result.addNode(item);
                    }
                }
            }
        }catch (Exception e){
            log.error("scan list page "+ page.getUrl() + " error... ");
        }
    }

    @Override
    public List<String> buildUrls() {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            urls.add(MessageFormat.format(LIST_PAGE,i+1));
        }
        result.setStatus(Result.State.START_1);
        return urls;
    }

    @Override
    public void end(){
        result.setStatus(Result.State.END_1);
    }

    @Override
    public void failedHandler() throws Exception {
        throw new Exception("失败");
    }

}
