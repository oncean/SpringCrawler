package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Result;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public abstract class AbstartTask implements PageProcessor {

    protected Result result;

    protected int maxRetryTime;

    public String taskName = "";

    public  AbstartTask(Result result){
        //默认重试时间为10次
        this(result,10);
    }

    public AbstartTask(Result result,int maxRetryTime){
        this.result = result;
        this.maxRetryTime = maxRetryTime;
    }

    public abstract void parse(Page page);
    public abstract List<String> buildUrls();
    public abstract void end();



    @Override
    public void process(Page page) {
        parse(page);
    }

    @Override
    public Site getSite() {
        return SpiderGenerator.getSite();
    }

    private void scan(List<String> pageUrl) throws Exception {
        Downloader downloader= SpiderGenerator.getHttpClientDownloader();
        int retryTime = 0;
        int total = pageUrl.size();
        do {
            if(retryTime>0){
                //第二次就从download error获取
                pageUrl = downloader.getErrors();
            }
            Spider.create(this)
                    .setDownloader(SpiderGenerator.getHttpClientDownloader())
                    // 添加初始化的URL
                    .addUrl((String[]) pageUrl.toArray(new String[0]))
                    // 使用单线程
                    .thread(total<10?total:10)
                    // 运行
                    .run();
            retryTime++;
        }while (!downloader.ifSuccess() && retryTime<=maxRetryTime);
        if(!downloader.ifSuccess()){
            System.out.println("重试"+taskName+"任务"+maxRetryTime+"次,仍然失败"+ downloader.getErrors());
            failedHandler();
        }
    }

    public void failedHandler() throws Exception {
        //nothing to do
    }

    public void start() throws Exception {
        scan(buildUrls());
        end();
    }

}
