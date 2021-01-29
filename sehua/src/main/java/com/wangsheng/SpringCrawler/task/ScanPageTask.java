package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.downloader.Downloader;
import com.wangsheng.SpringCrawler.generate.SpiderGenerator;
import com.wangsheng.SpringCrawler.model.MainPage;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.model.TaskState;
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
            List<Selectable> selectables = page.getHtml().css("th.new").nodes();
            selectables.addAll(page.getHtml().css("th.common").nodes());
            List<Node> nodes = new ArrayList<>();
            if(selectables != null && selectables.size()>0){
                for (Selectable selectable :selectables
                ) {
                    if(selectable.regex("隐藏置顶帖").get() == null){
                        String url = selectable.links().regex("https://sehuatang.org/(.*?).html",0).get();
                        nodes.add(new Node(url));
                    }
                }
            }
            scanItemSuccess(page.getUrl().get(),nodes);
            log.info("扫描"+page.getUrl()+"成功，扫描到了"+ nodes.size()+"个项目。。。");
        }catch (Exception e){
            scanItemError(page.getUrl().get(),e.getMessage());
            log.error("scan list page "+ page.getUrl() + " error... ");
        }
    }

    @Override
    public List<String> buildUrls() {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            String url = MessageFormat.format(LIST_PAGE,i+1);
            urls.add(url);
            addItem(url);
        }
        result.setStatus(TotalTask.State.START_1);
        return urls;
    }

    @Override
    public void end(){
        result.setStatus(TotalTask.State.END_1);
    }

    @Override
    public void errorHandler(String url) {
        for (MainPage page :
                result.getMainPageList()) {
            if (page.getUrl().equals(url)) {
                page.setState(TaskState.ERROR);
                break;
            }
        }
    }


    private void addItem(String url){
        synchronized (result.getMainPageList()){
            result.getMainPageList().add(new MainPage(url));
        }
    }

    private void scanItemSuccess(String url, List<Node> nodes){
        for (MainPage mainPage :
                result.getMainPageList()) {
            if(mainPage.getUrl().equals(url)){
                mainPage.setNodes(nodes);
                mainPage.setState(TaskState.SUCCESS);
                break;
            }
        }
    }

    private void scanItemError(String url, String errMsg){
        for (MainPage mainPage :
                result.getMainPageList()) {
            if(mainPage.getUrl().equals(url)){
                mainPage.setErrMsg(errMsg);
                mainPage.setState(TaskState.ERROR);
                break;
            }
        }
    }
}
