package com.wangsheng.SpringCrawler.generate;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class SpiderGenerator  {

    public static Site getSite(){
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
}
