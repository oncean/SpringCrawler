package com.wangsheng.SpringCrawler.crawlers;

import com.alibaba.fastjson.JSON;
import com.wangsheng.SpringCrawler.model.sehua.Result;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SehuaCrawler implements PageProcessor {

    private String List_Page = "https://sehuatang.org/forum-103-{0}.html";
    private String List_Page_RG = "https://sehuatang.org/forum-103-\\d+\\.html";

    private Site site;
    private HttpClientDownloader httpClientDownloader;

    private ArrayList<Result> cache;

    private ConcurrentHashMap<String,Boolean> pagesCache;


    public SehuaCrawler(){

        httpClientDownloader = new MyDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("127.0.0.1",10809)
        ));
        site = Site
                .me()
                .setDomain("sehuatang.org")
                .setSleepTime(3000)
                .setRetryTimes(5)
                .setRetrySleepTime(3000)
                .setTimeOut(30000)
                .setUserAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    }

    private Result build(Page page){
        Result result = new Result();
        result.setUrl(page.getUrl().toString());
        try{
            List<Selectable> nodes = page.getHtml().css("div.pct td").nodes();
            if(nodes != null && nodes.size()>0){
                Selectable node =  nodes.get(0);
                result.setImgList(node.css("img").regex("file=\"(.*?)\"").all());
                result.setMagnet(node.css("li").get());
                result.setTitle(node.regex("【影片名称】：(.*?)<br>").get());
                result.setArticle(node.regex("【出演女优】：(.*?)<br>").get());
                result.setStatus(1);
                return result;
            }
        }catch (Exception e){
        }
        log.error("scan page "+ page.getUrl() + " error... ");
        return result;
    }


    private List<String> buildList(Page page){
        List<String> items = new ArrayList<>();
        try{
            List<Selectable> nodes = page.getHtml().css("th.new").nodes();
            nodes.addAll(page.getHtml().css("th.common").nodes());
            if(nodes != null && nodes.size()>0){
                for (Selectable node :nodes
                     ) {
                    if(node.regex("隐藏置顶帖").get() == null){
                        items.add(node.links().regex("https://sehuatang.org/(.*?).html",0).get());
                    }
                }
                log.info("新增"+items.size()+"个扫描项....");
            }
        }catch (Exception e){
            log.error("scan list page "+ page.getUrl() + " error... ");
        }
        return items;
    }


    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(List_Page_RG).match()) {
            //System.out.println("解析列表页面"+ page.getUrl());
            List<String> children = buildList(page);
            for (String child :
                    children) {
                pagesCache.put(child,false);
            }
            page.addTargetRequests(children);
        }else{
            //System.out.println("解析页面"+ page.getUrl());
            synchronized (cache){
                String url = page.getUrl().get();
                if(!pagesCache.get(url)){
                    cache.add(build(page));
                    pagesCache.put(url,true);
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<Result> getAll(){
        List<Result> results = new ArrayList<>();
        for(int i =0 ;i<2;i++){
            int index=0;
            int times = 0;
            pagesCache = new ConcurrentHashMap<>();
            cache = new ArrayList<>();
            String url = MessageFormat.format(List_Page,i+1);
            while (index ==0 && times<3){
                System.out.println("尝试解析。。。。times"+ times);
                scan(url);
                boolean flag = true;
                for (Boolean item:   pagesCache.values()
                ) {
                    if(item == false){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    index = 1;
                }
                times++;
            }
            System.out.println("第"+(i+1)+"页扫描完成，生成"+cache.size()+"个对象");
            results.addAll(cache);
        }
        log.info(JSON.toJSONString(results));
        return results;
    }


    private void scan(String url){
        // 启动爬虫
        Spider.create(this)
                .setDownloader(httpClientDownloader)
                // 添加初始化的URL
                .addUrl(url)
                // 使用单线程
                .thread(10)
                // 运行
                .run();
    }





    /**
     * 主方法启动爬虫
     */
    public static void main(String[] args) {

        SehuaCrawler sehuaCrawler = new SehuaCrawler();
        List<Result> results = sehuaCrawler.getAll();
    }
}

