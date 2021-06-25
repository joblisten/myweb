package com.example.config;

import com.example.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 开启socket支持
 */

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //设置连接路径和处理
        registry.addHandler(new WebSocketServer(), "/socket")
                .setAllowedOrigins("*")
                .addInterceptors(new MyWebSocketInterceptor());
        //设置拦截器
    }

    /**
     * 自定义拦截器拦截WebSocket请求
     */
    class MyWebSocketInterceptor implements HandshakeInterceptor {
        //前置拦截一般用来注册用户信息，绑定 WebSocketSession
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            log.info("socket请求前置拦截,握手开始~~");
            if (!(request instanceof ServletServerHttpRequest)) {
                return true;
            }

            //HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            //String userName = (String) servletRequest.getSession().getAttribute("username");
            String userName = "Koishipyb";

            //获取socket的变量
            attributes.put("username", userName);
            return true;
        }

        /**
         * 后置拦截器
         *
         * @param request
         * @param response
         * @param wsHandler
         * @param exception
         */
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
            log.info("后置拦截器,握手结束~~");
        }
    }
}
