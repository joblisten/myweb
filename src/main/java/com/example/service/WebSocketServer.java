package com.example.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class WebSocketServer implements WebSocketHandler {
    private static final Map<String, WebSocketSession> concurrentHashMap = new ConcurrentHashMap<>();
    //原子性操作private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    /**
     * 相当于@OnOpen
     * 正式建立连接
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //通过session会话
        String userName = session.getAttributes().get("username").toString();
        //存储关系
        concurrentHashMap.put(userName, session);
       
        log.info(String.format("成功建立连接{}", userName));
    }

    /**
     *相当于@OnMessage
     * 接受客户端消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        session.sendMessage(message);
        log.info(msg);
    }

    /**
     * 相当于@OnError
     * 连接出错
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("连接出错");
        if (session.isOpen()) {
            session.close();

        }
    }


    /**
     * 相当于@Onclose
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("连接已关闭,status:" + closeStatus);
        WebSocketSession webSocketSession = concurrentHashMap.remove(session.getAttributes().get("username").toString());
        webSocketSession.close();
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     * @param message
     */
    public static void sendMessage(String userName, String message) {
        WebSocketSession webSocketSession = concurrentHashMap.get(userName);
        if (webSocketSession == null || !webSocketSession.isOpen())
        {return;}
        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 群发消息
     * @param message
     */
    public static void massMessage(String message) {
        concurrentHashMap.keySet().forEach(us -> sendMessage(us, message));
    }

}