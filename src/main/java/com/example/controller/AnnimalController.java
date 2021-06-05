package com.example.controller;


import com.example.dao.CatMapper;
import com.example.dao.ConstellationMapper;

import com.example.po.Cat;
import com.example.po.Constellation;
import com.example.po.Dog;
import com.example.service.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class AnnimalController {
    @Autowired
    CatService catService;
    @Autowired
    ConstellationService constellationService;
    @Autowired
    CatMapper catMapper;

    @Autowired
    ConstellationMapper constellationMapper;
    @Autowired
    DogService dogService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    CartService cartService;



    /*
    随机匹配
     */
    @GetMapping("/random")
    public String catRandom(Model model,
                            @RequestParam(defaultValue = "cat",value = "style")String style){
        if(style.equals("dog")){
            List<Dog> catList=dogService.randomSd();
            String styl=catList.get(0).getStyle();
            model.addAttribute("s",styl);
            model.addAttribute("cat",catList);
            return "random";

        }
        List<Cat> catList=catService.randomS();
        String styl=catList.get(0).getStyle();
        model.addAttribute("s",styl);
        model.addAttribute("cat",catList);
        return "random";
    }

    /*
    星座匹配
     */
    @GetMapping(value = "/cons")
    public  String constellationMatch(HttpServletRequest request, Model model,
                                      @RequestParam(defaultValue = "cat",value = "style")String style){
       /*
       获取生日
        */
        String mouth=request.getParameter("mouth");
        String day=request.getParameter("day");
        int monthh=Integer.parseInt(mouth);
        int dayy=Integer.parseInt(day);
        model.addAttribute("m",monthh);
        model.addAttribute("n",day);
        log.info("mouth={}",monthh);  //加一个{}才能把参数里面的值打印出来
        log.info("day={}",dayy);

        //根据生日获取星座
        String cname=constellationService.getConstellation(monthh,dayy);
        log.info(cname);

        if (style.equals("dog")){
           //根据星座获取了狗狗
            List<Dog> catCon=dogService.selectByd(cname);
            //根据星座获取星座性格描述
            List<Constellation> list=constellationMapper.selectByName(cname);
            log.info("catCon={}",catCon);
            log.info("list={}",list);
            model.addAttribute("st",style);
            model.addAttribute("catCons",catCon);
            model.addAttribute("lists",list);
            return "cons";
        }

        //根据星座获取了猫咪
        List<Cat> catCon=catService.selectBycname(cname);
        //根据星座获取星座性格描述
        List<Constellation> list=constellationMapper.selectByName(cname);
        log.info("catCon={}",catCon);
        log.info("list={}",list);
        model.addAttribute("st",style);
       model.addAttribute("catCons",catCon);
       model.addAttribute("lists",list);
        return "cons";
    }



    /*
    宠物商城
     */
    @GetMapping(value = "/portfolio")
    public String dcat(Model model,
                       @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum ,
                       @RequestParam(defaultValue = "cat",value = "style") String style){
        if(style.equals("dog")){
            PageInfo<Dog> pageInfo=dogService.selectDog(pageNum);
            List<Dog> dcat=pageInfo.getList();
            model.addAttribute("pageinfo", pageInfo);
            model.addAttribute("dcats", dcat);
        }else {
            PageInfo<Cat> pageInfo = catService.selectDcat(pageNum);
            List<Cat> dcat = pageInfo.getList();
            model.addAttribute("pageinfo", pageInfo);
            model.addAttribute("dcats", dcat);
        }
        return "portfolio";

    }

    /*
    买宠物详情
     */
    @GetMapping(value = "/product")
    public String selectId(@RequestParam("id") int id,Model model,
            @RequestParam(defaultValue = "cat",value = "style") String style){
        if(style.equals("dog")){  //宠物狗
            List<Dog> lis=dogService.selectById(id);

            model.addAttribute("styl",style);  //供ajax使用
            model.addAttribute("liss",lis);
        }
        else{
       List<Cat> lis=catService.selectById(id);
            String stly=style;
            model.addAttribute("styl",style);
             model.addAttribute("liss",lis);
        }
       return "product";
    }
    /*
    在购物详情里加入购物车
     */
    @GetMapping(value = "/add",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String selectCart(Model model,
                                          HttpServletRequest request){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String uname=user.getUsername();//用户名字


        String style=request.getParameter("styl").substring(1,4);  //宠物类型
        Integer num=Integer.parseInt(request.getParameter("num"));  //宠物数量,
       /*
       正则匹配,解决宠物id不全问题
        */
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(request.getParameter("pid").substring(6,10));
        String ppid=m.replaceAll("").trim();
        Integer pid=Integer.parseInt(ppid);  //宠物id
          /*
          回调函数
           */
        String callback=request.getParameter("callback");

        log.info("num={}",num);
        log.info("pid={}",pid);
        log.info("style={}",style);


       if(style.equals("dog")){
           List<Dog> goods=dogService.selectById(pid); //宠物信息
           String pname=goods.get(0).getName();
           String url=goods.get(0).getUrl();
           double price=goods.get(0).getPrice();
           double total=price*num;  //总价
           int goodss=cartService.setGoods(pid,pname,uname,url,num,price,total);

       }
       else{
           List<Cat> goods=catService.selectById(pid);
           String pname=goods.get(0).getName();
           String url=goods.get(0).getUrl();
           double price=goods.get(0).getPrice();
           double total=price*num;  //总价
           int goodss=cartService.setGoods(pid,pname,uname,url,num,price,total);

       }
      String res=callback+"({\"name\":\"加入成功\"})";
        return res;

    }

    /*
   在幸运匹配中加入购物车
    */
    @GetMapping(value = "/addgood", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addCart(HttpServletRequest request) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;//当前用户
        String uname = user.getUsername();//用户名字


        String style = request.getParameter("style");  //宠物类型
        Integer id = Integer.parseInt(request.getParameter("id"));  //宠物id
        Integer num = 1;  //宠物数量,
        log.info("num={}", num);
        log.info("id={}",id);
        log.info("style={}",style);

        if ("dog".equals(style.replace("\"", "").replace("\"", ""))) {
            List<Dog> goods = dogService.selectById(id); //宠物信息
            String pname = goods.get(0).getName();
            String url = goods.get(0).getUrl();
            double price = goods.get(0).getPrice();
            double total = price * num;  //总价
            int good = cartService.setGoods(id, pname, uname, url, num, price, total);

        } else {
            List<Cat> goods = catService.selectById(id);
            String pname = goods.get(0).getName();
            String url = goods.get(0).getUrl();
            double price = goods.get(0).getPrice();
            double total = price * num;  //总价
            int good = cartService.setGoods(id, pname, uname, url, num, price, total);

        }
        String re = "[{\"name\":\"加入成功\"}]";
        log.info(re);
        return re;


    }

}
