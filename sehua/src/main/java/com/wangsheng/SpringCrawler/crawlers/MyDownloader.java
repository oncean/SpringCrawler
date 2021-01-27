package com.wangsheng.SpringCrawler.crawlers;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

@Slf4j
class MyDownloader  extends HttpClientDownloader {

    @Override
    protected void onError(Request request) {
        log.error("111111111111111111111111");
    }

}
