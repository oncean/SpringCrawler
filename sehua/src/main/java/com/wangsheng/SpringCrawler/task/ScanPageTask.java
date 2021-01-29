package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.model.MainPage;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.model.ScanItemState;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScanPageTask extends AbstractTask {
    private final static String LIST_PAGE = "https://sehuatang.org/forum-103-{0}.html";

    public ScanPageTask(Result result) {
        super(result);
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
        result.setStatus(TotalTask.State.START_1);
        for (MainPage page : result.getMainPageList()){
            if(page.getState() == ScanItemState.NEW || page.getState() == ScanItemState.ERROR){
                urls.add(page.getUrl());
                page.setState(ScanItemState.LOADING);
            }
        }
        return urls;
    }

    public static List<MainPage> generate(int []pageNums){
        List<MainPage> pages = new ArrayList<>();
        for (int i : pageNums) {
            String url = MessageFormat.format(LIST_PAGE,i);
            pages.add(new MainPage(url));
        }
        return pages;
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
                page.setState(ScanItemState.ERROR);
                break;
            }
        }
    }


    private void scanItemSuccess(String url, List<Node> nodes){
        for (MainPage mainPage :
                result.getMainPageList()) {
            if(mainPage.getUrl().equals(url)){
                synchronized (result){
                    result.getNodes().addAll(nodes);
                    mainPage.setNodeNum(nodes.size());
                }
                mainPage.setState(ScanItemState.SUCCESS);
                break;
            }
        }
    }

    private void scanItemError(String url, String errMsg){
        for (MainPage mainPage :
                result.getMainPageList()) {
            if(mainPage.getUrl().equals(url)){
                mainPage.setErrMsg(errMsg);
                mainPage.setState(ScanItemState.ERROR);
                break;
            }
        }
    }
}
