package com.wangsheng.SpringCrawler.websocket.sockets;

import com.alibaba.fastjson.JSON;
import com.wangsheng.SpringCrawler.task.TotalTask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/websocket/scan")
@Component
@Slf4j
public class ScanServerEndpoint {

    private static ConcurrentHashMap<String, TotalTask> map = new ConcurrentHashMap<String, TotalTask>();

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText(UUID.randomUUID().toString());
        System.out.println("连接成功,生成taskId:taskId");
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("连接关闭");
    }

    /**
     * 接收到消息
     *
     * @param
     */
    @OnMessage
    public void onMsg(String json, Session session) throws IOException {
        Message message = JSON.parseObject(json,Message.class);
        switch (message.type){
            case START:
                startTask(message,session);
                break;
            case STOP:
                break;
            default:
                break;
        }

    }

    private void startTask(Message message, final Session session){
        final TotalTask task = new TotalTask(message.total,session);
        task.start();
        map.put(message.taskId,task);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(task.getResult()));
                } catch (IOException e) {
                    log.error("发送消息给前端失败",e);
                }
            }
        },0,1000);
    }


    @Data
    private static class Message{
        private enum TYPES{
            START,STOP
        }

        private TYPES type;
        private String taskId;
        private int total;//多少页
    }
}
