package com.wangsheng.SpringCrawler.websocket.sockets;

import com.alibaba.fastjson.JSON;
import com.wangsheng.SpringCrawler.MyApplicationContextAware;
import com.wangsheng.SpringCrawler.service.TaskService;
import com.wangsheng.SpringCrawler.task.TotalTask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@ServerEndpoint("/websocket/scan/{taskId}")
@Component
@Slf4j
public class ScanServerEndpoint {


    private TaskService taskService;

    @Autowired
    public ScanServerEndpoint(){
        this.taskService = (TaskService) MyApplicationContextAware.getApplicationContext().getBean("taskService");
    }

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("taskId") String taskId) throws IOException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(taskService.getResult(taskId)));
                } catch (IOException e) {
                    log.error("发送消息给前端失败",e);
                }
            }
        },0,1000);
    }

}
