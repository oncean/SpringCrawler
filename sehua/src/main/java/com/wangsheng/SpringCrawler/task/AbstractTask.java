package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.Result;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;

@Slf4j
public abstract class AbstractTask implements PageProcessor {

    protected Result result;

    public  AbstractTask(Result result){
        //默认重试时间为10次
        this.result = result;
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

    private void scan(List<String> pageUrl) {
        InnerDownloader downloader = new InnerDownloader();
        downloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("127.0.0.1",10809)
        ));
        int total = pageUrl.size();
        if(total > 0){
            Spider.create(this)
                    .setDownloader(downloader)
                    // 添加初始化的URL
                    .addUrl((String[]) pageUrl.toArray(new String[0]))
                    // 使用单线程
                    .thread(total<10?total:10)
                    // 运行
                    .run();
        }

    }

    public void start() {
        scan(buildUrls());
        end();
    }


    public class InnerDownloader  extends HttpClientDownloader {


        @Override
        public Page download(Request request, Task task) {
            log.info("开始下载"+ request.getUrl());
            return super.download(request,task);
        }
        @Override
        protected void onError(Request request) {
            errorHandler(request.getUrl());
        }
    }
}
