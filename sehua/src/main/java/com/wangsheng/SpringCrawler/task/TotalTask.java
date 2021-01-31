package com.wangsheng.SpringCrawler.task;

import com.wangsheng.SpringCrawler.model.Result;
import lombok.Data;

import java.util.*;

@Data
public class TotalTask{
    public enum State {
        NEW,START_1,END_1,START_2,END,ERROR_1,ERROR_2
    }
    private String taskId;
    private Result result;
    private int []pageNums;

    private Thread currentThread;


    public TotalTask(int []pageNums){
        this.taskId = UUID.randomUUID().toString();
        this.pageNums =pageNums;
        this.result = new Result();
        this.result.setMainPageList(ScanPageTask.generate(pageNums));
    }

    public void start(){
        if(currentThread == null){
            currentThread = new TaskThread();
            currentThread.start();
        }else{
            if(!currentThread.isAlive()){
                //如果停止 则重新启动
                currentThread = new TaskThread();
                currentThread.start();
            }
        }
    }

    public boolean isAlive(){
        if(currentThread == null){
            return true;
        }else {
            return currentThread.isAlive();
        }
    }


    public Result getResult() {
        result.setCurrent(System.currentTimeMillis());
        return result;
    }


    public class TaskThread extends Thread{
        @Override
        public void run(){
            ScanPageTask  task1 = new ScanPageTask(result);
            task1.start();
            ScanItemTask task2 = new ScanItemTask(result);
            task2.start();
        }
    }
}
