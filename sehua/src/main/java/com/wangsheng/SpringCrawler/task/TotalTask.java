package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.model.MainPage;
import com.wangsheng.SpringCrawler.model.Node;
import com.wangsheng.SpringCrawler.model.Result;
import com.wangsheng.SpringCrawler.model.TaskState;
import lombok.Data;

import javax.websocket.Session;
import java.util.Arrays;
import java.util.Currency;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
public class TotalTask extends Thread{
    public enum State {
        NEW,START_1,END_1,START_2,END
    }
    private String taskId;
    private Result result = new Result();
    private int total;
    public TotalTask(int total){
        this.taskId = UUID.randomUUID().toString();
        this.total =total;
    }

    @Override
    public void run(){
        try{
            ScanPageTask  task1 = new ScanPageTask(result,total);
            task1.start();
            for (MainPage mainPage:
                 result.getMainPageList()) {
                result.getNodes().addAll(mainPage.getNodes());
                mainPage.setNodes(Arrays.asList(new Node[mainPage.getNodes().size()]));
            }
            ScanItemTask task2 = new ScanItemTask(result,10);
            task2.start();
        }catch (Exception e){
            result.setErrors(true);
        }
    }

    public Result getResult() {
        result.setCurrent(System.currentTimeMillis());
        return result;
    }
}
