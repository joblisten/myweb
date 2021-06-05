package com.example.controller;


import com.example.po.*;
import com.example.service.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
public class AdminController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    BookService bookService;

    @Autowired
    ExcelService excelService;

    @Autowired
    BlogService blogService;

    @Autowired
    DogService dogService;
    @Autowired
    CatService catService;
    @Autowired
    ImageService imageService;
    @GetMapping(value = "/admin/ad")
    public String gg(){
        return "conpay";
    }





    /*
   添加商品
    */
    @PostMapping(value = "/admin/ad")
    @ResponseBody
    public String adminAdd( HttpServletRequest request,@RequestParam("file") MultipartFile file){
        //商品添加需要：名字，url，类型，价格
        double price=Double.parseDouble(request.getParameter("price"));
        String style=request.getParameter("style");
        String name=request.getParameter("pname");
        log.info("name={}",name);
        log.info("style={}",style);
        log.info("price={}",price);

        if(style.equals("dog")){
           String url=imageService.imgageUpdog(file);
           int r=dogService.settDog(name,url,price,style);
           if(r==0)
               log.info("添加狗狗失败");
        }
        else {
            String url=imageService.imgageUpcat(file);
            int r=catService.settCat(name,url,price,style);
            if(r==0)
                log.info("添加猫咪失败");

        }

        String re="[{\"admin\":\"添加商品成功\"}]";
        log.info(re);
        return re;

    }

    /*
    商品价格修改,根据id
     */
    @GetMapping(value = "/admin/ch",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String adminChan( HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("id"));
        double price=Double.parseDouble(request.getParameter("price"));
        String style=request.getParameter("style");
        if(style.equals("dog")){
            int row=dogService.upDog(id,price);
            if(row>0)
                log.info("狗狗价格修改成功");

        }
        else {
            int row=catService.upCat(id,price);
            if(row>0)
                log.info("猫咪价格修改成功");

        }

        String re="[{\"admin\":\"修改价格成功\"}]";
        log.info(re);
        return re;

    }



    /*
    商品管理
     */
    @GetMapping(value = "/admin/cart")
    public String findALLcart(Model model,HttpServletRequest request,
                         @RequestParam(defaultValue = "1",value = "pageNum")int pageNum,
                              @RequestParam(defaultValue = "cat",value = "style")String style){
        if(style.equals("dog")){
            PageInfo<Dog> pageInfo=dogService.selectDog(pageNum);
            List<Dog> dcat=pageInfo.getList();
            log.info("list={}",dcat);
            model.addAttribute("pagein", pageInfo);
            model.addAttribute("product", dcat);
        }else {
            PageInfo<Cat> pageInfo = catService.selectDcat(pageNum);
            List<Cat> dcat = pageInfo.getList();
            log.info("list={}",dcat);
            model.addAttribute("pagein", pageInfo);
            model.addAttribute("product", dcat);
        }

        return "concart";
    }




    /*
   根据用户、标题查找博客
    */
    @GetMapping(value = "/admin/seblog")
    public String seblog(Model model,HttpServletRequest request,
                              @RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){
        String sename=request.getParameter("name");
        log.info(sename);
        PageInfo<Blog> pageInn=blogService.selectblog(sename,pgeNum);
        List<Blog> blogs=pageInn.getList();
        log.info("bo={}",blogs);
        model.addAttribute("pageinn",pageInn);
        model.addAttribute("b",blogs);

        return "conblog";
    }



    /*
    删除博客
     */
    @GetMapping(value = "/admin/blogde",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String blogde(HttpServletRequest request){
        Integer id=Integer.parseInt(request.getParameter("id")); //博客id
        int row=blogService.blogDe(id);
        if (row>0)
            log.info("博客删除成功={}",id);


        String re="[{\"admin\":\"删除博客成功\"}]";
        log.info(re);
        return re;
    }

    /*
    博客管理
     */
    @GetMapping(value = "/admin/blog")
    public String findAllblog(Model model,
                              @RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){
        PageInfo<Blog> pageInn=blogService.selectAll(pgeNum);
        List<Blog> blogs=pageInn.getList();
        model.addAttribute("pageinn",pageInn);
        model.addAttribute("b",blogs);
        return "conblog";
    }



    /*
    显示用户管理
     */
    @GetMapping(value = "/admin/user")
    public String findAll(Model model){
        List<SysUser> use=sysUserService.findUser();
        log.info("list={}",use);

        model.addAttribute("userr",use);
        return "conuser";
    }

    /*
    删除用户
     */
    @GetMapping(value = "/admin/userde",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String userde(HttpServletRequest request){
        Integer id=Integer.parseInt(request.getParameter("id")); //用户id
        int row=sysUserService.userde(id);
        if (row>0)
            log.info("用户删除成功={}",id);


        String re="[{\"admin\":\"删除成功\"}]";
        log.info(re);
        return re;
    }


    /*
    管理员管理订单
     */
    @GetMapping(value = "/admin/order")
    public String finAll(Model model,@RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){

        PageInfo<Book> pageInn=bookService.finAll(pgeNum);
        List<Book> bo=pageInn.getList();
        model.addAttribute("pageinn",pageInn);
        model.addAttribute("bok",bo);

        return "conorder";
    }

    /*
    根据订单号或者用户名搜索订单
     */
    @GetMapping(value = "/admin/se")
    public String se(Model model,HttpServletRequest request,@RequestParam(defaultValue = "1",value = "pageNum")int pgeNum) {

          String sename=request.getParameter("name");
          log.info(sename);
        PageInfo<Book> pageInn=bookService.selectse(sename,pgeNum);
        List<Book> bo=pageInn.getList();
        log.info("bo={}",bo);
        model.addAttribute("pageinn",pageInn);
        model.addAttribute("bok",bo);

        return "conorder";
    }

    /*
    单个订单受理
     */
    @GetMapping(value = "/admin/adminse",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String adminse(HttpServletRequest request){
       Integer id=Integer.parseInt( request.getParameter("id")); //订单id
        String dealw="已受理";
        int roe=bookService.upDealw(id,dealw);
        if (roe>0)
            log.info("受理成功={}",id);


        String re="[{\"admin\":\"受理成功\"}]";
        log.info(re);
        return re;



    }

    /*
   未处理订单
    */
    @GetMapping(value = "/admin/adminord")
    public String adminord(Model model,HttpServletRequest request,@RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){
        String dealw="未受理";
        PageInfo<Book> pageInn=bookService.selectDeal(dealw,pgeNum);
        List<Book> bo=pageInn.getList();
        log.info("bo={}",bo);
        model.addAttribute("pageinn",pageInn);
        model.addAttribute("bok",bo);
        return "conorder";

    }

    /*
    一键受理
     */
    @GetMapping(value = "/admin/adminall",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String adminAlldealw(){
        String dealw="已受理";
        int row=bookService.upAlldealw(dealw);
        if (row>0)
            log.info("一键受理成功={}",row);

        String re="[{\"admin\":\"一键受理成功\"}]";
        log.info(re);
        return re;


    }

    /*
   一键导出excel
    */
    @GetMapping(value = "/admin/adminexcel")
    public String adminExcel(HttpServletResponse response) throws IOException {
        String fileName = "订单报表.xlsx";
        HSSFWorkbook workbook=excelService.show();

        OutputStream outputStream =null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook!=null)
            log.info("一键导出成功={}",workbook);
        return "conorder";

    }



    }
