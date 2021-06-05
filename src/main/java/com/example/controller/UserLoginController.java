package com.example.controller;


import com.example.service.RegisterService;
import com.example.service.SysUserService;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Controller
@SessionAttributes("username")
public class UserLoginController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private RegisterService registerService;

/*

    @GetMapping("/index")
    public String in(Model model,HttpServletRequest httpServletRequest){
        String username=httpServletRequest.getParameter("username");
        model.addAttribute("username",username);
        return "index";
    }
*/



    @Autowired
    private Producer kaptchaProduce;

    @RequestMapping(value = "/log.jpg", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){ //返回给浏览器的是图片，不是html，需要用response


        //生成验证码
        String captext = kaptchaProduce.createText();
        BufferedImage image = kaptchaProduce.createImage(captext);

        //将验证码存入session
        session.setAttribute("kaptcha",captext);

        //将图片输出给浏览器
        response.setContentType("image/jpeg");
        try{
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"jpg",os);
        }catch(IOException e){
            log.error("相应验证码失败",e.getMessage());
        }

    }


    /*
    用户注册
     */
    @PostMapping(value = "/registert")
    public String regis(HttpServletRequest httpServletRequest ,Model model){
        String username=httpServletRequest.getParameter("username");
        String password=httpServletRequest.getParameter("password");

        String mg=" ";
        if((username==null||username.length()<=0)||(password==null||password.length()<=0)){
             mg="nut";
            log.info("没有参数{}",username);
            model.addAttribute("msg",mg);
            return "register";
        }

        if(userService.selectByName(username)!=null){
             mg="exs";
            log.info("用户已经存在{}",username);
            model.addAttribute("msg",mg);
            return "register";
        }
        else{
        registerService.regis(username,password);
        log.info("注册成功{}",username);
        }


        return "logint";



    }

}
