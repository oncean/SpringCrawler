package com.wangsheng.task;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;

@Slf4j
public abstract class AbstractTask implements PageProcessor {


    public  AbstractTask(){
    }


    public abstract void parse(Page page);
    public abstract List<String> buildUrls();
    public abstract void end();
    public abstract void errorHandler(String url);

    public void onStart(Request request){
        log.info("开始下载"+ request.getUrl());
    }


    @Override
    public void process(Page page) {
        parse(page);
    }

    @Override
    public Site getSite() {
        return Site
                .me()
                .setDomain("sehuatang.org")
                .setSleepTime(3000)
                .setRetryTimes(5)
                .setRetrySleepTime(3000)
                .setTimeOut(300000)
                .setUserAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

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
            onStart(request);
            return super.download(request,task);
        }
        @Override
        protected void onError(Request request) {
            errorHandler(request.getUrl());
        }
    }
}
