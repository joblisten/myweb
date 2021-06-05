package com.example.controller;


import com.example.dao.TestMapper;
import com.example.po.Blog;
import com.example.po.Commentary;
import com.example.po.Testpo;
import com.example.service.BlogService;
import com.example.service.CommentaryService;
import com.example.service.ImageService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Slf4j
@Controller
public class BlogController {

    @Autowired
    ImageService imageService;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentaryService   commentaryService;


    /*
    博客评论
     */

    @ResponseBody
    @GetMapping(value = "/reply",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Commentary> replyBlog (Model model,HttpServletRequest request)throws ParseException{
        String content=request.getParameter("content");
        Integer bid=Integer.valueOf(request.getParameter("id"));

        if(content.isEmpty()){

            List result=blogService.selectByAid(bid);
            log.info("result{}",result);

            return result;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String uname=user.getUsername();//用户名字


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        Date bdate= dff.parse(date);
        blogService.replyBlog(uname,bid,bdate,content);
        log.info("bid={}",bid);
        log.info("content={}",content);
        List result=blogService.selectByAid(bid);
        log.info("result{}",result);
        return result;

    }



    /*
    博客论坛页
     */
    @GetMapping(value = "/blog")
    public String selcBlog(Model model,@RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){
        List<Blog> isTopBlogs=blogService.isTopBlog(); //置顶博客
        model.addAttribute("b",isTopBlogs);

        /*
        不置顶博客
         */
        PageInfo<Blog> pageIn=blogService.selectBlog(pgeNum);  //分页了
        List<Blog> blog=pageIn.getList();  //在pagein中获取博客文章
        model.addAttribute("bb",blog);
        model.addAttribute("pagein",pageIn);
        return "blog";
    }


    /*
    博客详情页
     */
    @GetMapping(value = "/blogg")
    public String introBlog(@RequestParam("id")  int id,Model model){

        //根据博客id获取博客浏览量
       int pageview=blogService.gView(id);
        blogService.upview(id,pageview+1);//更新浏览量+1


        //根据博客id获取博客详情
        List<Blog> inBlog=blogService.selectByAid(id);
        model.addAttribute("inb",inBlog);

        String  count=new String();

        //获取评论
        List<Commentary>  incom=commentaryService.selectByid(id);
        if(incom.size()==0){
            count=null;
            model.addAttribute("bid",id);
            model.addAttribute("co",count);
            return "boke";
        }
        Integer bid=incom.get(0).getBid();
        model.addAttribute("bid",bid);
          count=String.valueOf(incom.size());
        count=count+"条评论";
        log.info(count);
        model.addAttribute("co",count);
        model.addAttribute("inc",incom);

        return "boke";
    }
    /*
    写博客
     */
    @PostMapping(value = "/myblo")
    public String setBlog(HttpServletRequest request,Model model,@RequestParam("file") MultipartFile file) throws ParseException {
        String aname=request.getParameter("aname");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        String introduction=request.getParameter("introduction");
        log.info("file=",file);

        //图片地址获取
        String aimages=imageService.imgageUp(file);
        //日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        Date adate= dff.parse(date);

        log.info(title);log.info(aname);log.info(content);
        log.info(introduction);log.info(aimages);
        log.info("adate={}",adate);

        String istop="0";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String name=user.getUsername(); //当前用户名字
        String role=user.getAuthorities().toString();
        if (role.equals("[ROLE_ADMIN]")){
            istop="1";
            int bo=blogService.setBlog(aname,title,introduction,content,adate,aimages,istop);//存取博客
            List<Blog> myblog=blogService.selectMyblog(aname);
            model.addAttribute("myblogs",myblog);
            return "redirect:/myblog";
        }
        int bo=blogService.setBlog(aname,title,introduction,content,adate,aimages,istop);//存取博客
        List<Blog> myblog=blogService.selectMyblog(aname);
        model.addAttribute("myblogs",myblog);
        return "redirect:/myblog";



/*
        //拿博客
        List<Blog> myblog=blogService.selectMyblog(aname);
        model.addAttribute("myblogs",myblog);
        return "redirect:/myblog";*/

    }
    /*
    自己博客页,判断用户类型
     */
    @GetMapping(value = "/myblog")
    public String myblog(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String name=user.getUsername(); //当前用户名字
        String role=user.getAuthorities().toString();
        log.info("rolee={}",role);

        if (role.equals("[ROLE_ADMIN]")){
            log.info("yes");
            model.addAttribute("names",user.getUsername()); //防止没有写过博客的，博客表没有记录
            List<Blog> myblog=blogService.selectMyblog(name);
            model.addAttribute("myblogs",myblog);
            return "/admin";
        }
        log.info("no");
        model.addAttribute("names",user.getUsername()); //防止没有写过博客的，博客表没有记录
        List<Blog> myblog=blogService.selectMyblog(name);
        model.addAttribute("myblogs",myblog);
        return "myblog";
    }


}
