package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Result;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public abstract void errorHandler(String url);


    @Override
    public void process(Page page) {
        parse(page);
    }

    @Override
    public Site getSite() {
        return SpiderGenerator.getSite();
    }

    private void scan(List<String> pageUrl) throws Exception {
        InnerDownloader downloader = new InnerDownloader();
        downloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("127.0.0.1",10809)
        ));
        int total = pageUrl.size();

        Spider.create(this)
                .setDownloader(downloader)
                // 添加初始化的URL
                .addUrl((String[]) pageUrl.toArray(new String[0]))
                // 使用单线程
                .thread(total<10?total:10)
                // 运行
                .run();
    }

    public void start() throws Exception {
        scan(buildUrls());
        end();
    }


    public class InnerDownloader  extends HttpClientDownloader {
        @Override
        protected void onError(Request request) {
            errorHandler(request.getUrl());
        }
    }
}
