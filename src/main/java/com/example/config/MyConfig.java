package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
springmvc的配置
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

    private String filePath="E:/Idea workspace/demoweb2/src/main/resources/static/images/";

    private String filePathRe="/images/**";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/logint").setViewName("logint");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/about").setViewName("about");
        registry.addViewController("/contact").setViewName("contact");
        registry.addViewController("/wrong").setViewName("product");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/conss").setViewName("conss");
        registry.addViewController("/cons").setViewName("cons");
        registry.addViewController("/blog").setViewName("blog");
        registry.addViewController("/product").setViewName("product");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/dele").setViewName("dele");
        registry.addViewController("/pay").setViewName("pay");
        registry.addViewController("/admin/user").setViewName("conuser");
        registry.addViewController("/com").setViewName("conss");









    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //告知系统static 当成 静态资源访问,不然静态图片回显失败
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(filePathRe).addResourceLocations("file:"+filePath);
    }
}