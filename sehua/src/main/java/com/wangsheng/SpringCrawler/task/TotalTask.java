package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.model.Result;

import javax.websocket.Session;
import java.util.Currency;
import java.util.concurrent.TimeUnit;

public class TotalTask extends Thread{
    private String taskId;
    private Result result = new Result();
    private int total;
    private Session session;
    public TotalTask(int total,Session session){
        this.total =total;
        this.session =session;
    }

    public TotalTask(int i) {
    }

    @Override
    public void run(){
        try{
            ScanPageTask  task1 = new ScanPageTask(result,total);
            task1.start();
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
