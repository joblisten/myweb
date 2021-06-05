package com.example.component;

import lombok.extern.slf4j.Slf4j;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
自定义登录失败处理
 */
@Slf4j
@Component
public class UserFailHander implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
       log.info("账号密码错误");
        httpServletResponse.sendRedirect("/logint");

    }


}
