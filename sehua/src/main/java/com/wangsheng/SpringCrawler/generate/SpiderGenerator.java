package com.wangsheng.SpringCrawler.generate;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class SpiderGenerator  {
    public static Downloader getHttpClientDownloader(){
        Downloader httpClientDownloader = new Downloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("127.0.0.1",10809)
        ));
        return httpClientDownloader;
        // 添加初始化的URL
    }

    public static Site getSite(){
        return Site
                .me()
                .setDomain("sehuatang.org")
                .setSleepTime(3000)
                .setRetryTimes(5)
                .setRetrySleepTime(3000)
                .setTimeOut(30000)
                .setUserAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    }
}
