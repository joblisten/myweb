package com.example.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/*
自定义登录成功处理
 */
@Component
@Slf4j
public class UserSueccedHander implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

       /*前后的分离时使用：
       httpServletResponse.setContentType("application/json;charse=UTF-8");
       PrintWriter  printWriter =httpServletResponse.getWriter();
       printWriter.write("");这里自定义返回json数据给前端

       */
        log.info("登录成功");
        String username=httpServletRequest.getParameter("username");
        String password=httpServletRequest.getParameter("password");

        httpServletResponse.sendRedirect("/index");
    }
}
