package com.wangsheng.SpringCrawler.downloader;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Downloader  extends HttpClientDownloader {

    private List<String> errors = new ArrayList<>();

    @Override
    protected void onError(Request request) {
        log.error("111111111111111111111111");
        synchronized (errors){
            errors.add(request.getUrl());
        }
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean ifSuccess(){
        if(errors.size()>0){
            return false;
        }
        return true;
    }
}
