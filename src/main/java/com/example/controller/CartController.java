package com.example.controller;


import com.example.po.Book;
import com.example.po.Cart;
import com.example.service.BookService;
import com.example.service.CartService;
import com.example.service.SysUserService;
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
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    BookService bookService;
    /*
    当点击购物车时
     */
    @GetMapping("/cart")
    public String showGoods(Model model,@RequestParam(defaultValue = "1",value = "pageNum")int pgeNum){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String uname=user.getUsername();//用户名字

        PageInfo<Cart> pageIn =cartService.selectByName(pgeNum,uname);
        List<Cart> cartList=pageIn.getList();

        double pay=0;
        for (Cart to:cartList
        ) {
            pay=pay+to.getTotal();
        }
        model.addAttribute("carts",cartList);
        model.addAttribute("pays",pay);
        model.addAttribute("pagein",pageIn);


        return "cart";
    }
    /*
    购物车修改时
     */
    @GetMapping(value = "/chan",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String chanCart( HttpServletRequest request){
        Integer id=Integer.parseInt(request.getParameter("id"));
        Integer num=Integer.parseInt(request.getParameter("num"));
        Double price=Double.parseDouble(request.getParameter("price"));
        double total=num*price;
        log.info("id={}",id);
        log.info("num={}",num);
        log.info("price={}",price);
        log.info("total={}",total);
        int upcart=cartService.updateCart(id,num,total);
        if(upcart>0)
            log.info("更新了{}",upcart+"条数据");
        else
            log.info("更新失败");

         /*
          回调函数
           */
        String callback=request.getParameter("callback");
        String res=callback+"({\"name\":\"修改购物车\"})";
        return res;
    }
    /*
    删除根据id
     */
    @GetMapping(value = "/dele",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleCart(HttpServletRequest request){
        Integer id=Integer.parseInt(request.getParameter("id"));
        int dcart=cartService.delectCart(id);
         /*
          回调函数
           */
        String callback=request.getParameter("callback");
        String re=callback+"({\"name\":\"删除\"})";
        return re;

    }
    /*
    单个预购买，支付
     */
    @GetMapping(value = "/buy",produces = MediaType.APPLICATION_JSON_VALUE)

    public String singlePay(HttpServletRequest request,Model model){
        Integer id=Integer.parseInt(request.getParameter("id"));//商品id
        model.addAttribute("idd",id);
        List<Cart> cartList=cartService.selectById(id);
        model.addAttribute("cartlists",cartList);
        return "pay";

    }
    /*
    完成购买，生成订单
     */
    @PostMapping(value = "/pay")
    public String singOrder(HttpServletRequest request) throws ParseException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String uname=user.getUsername();//用户名字
        Integer id=Integer.parseInt(request.getParameter("id"));//商品id
        Cart cart=cartService.selectAll(id);


        String pname=cart.getPname();//商品名
        Integer num=cart.getNum();//商品数量
        Double total=cart.getTotal();//商品总价
        String url=cart.getUrl();

        String name=request.getParameter("name");//收件人
        String number=request.getParameter("number");//收件电话
        String address=request.getParameter("address");//收件地址
        String other=request.getParameter("other");//备注
        Double pay=Double.parseDouble(request.getParameter("money"));//商品总价
        log.info("pname={}",pname);
        log.info("address={}",address);
        //日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        Date bdate= dff.parse(date);


        if(pay.equals(total)){
            String sta="已支付";
            String dealw="未处理";
            String onumber=UUID.randomUUID().toString().substring(1,6) ;//订单号
            log.info("order={}",onumber);
            int orde=bookService.setOrder(uname,name,pname,num,total,number,address,other,sta,url,onumber,bdate,dealw);
            log.info("生成订单成功");
            int a=sysUserService.setNA(number,address,uname);  //添加信息到用户表
            int dcart=cartService.delectCart(id); //购买成功后删除购物车内商品
            log.info("删除订单的购物车成功");
        }

        return "redirect:/order";
    }

    /*
    显示订单
     */
    @GetMapping(value = "/order")
    public String showOrder(HttpServletRequest request,Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user= (User) principal;//当前用户
        String uname=user.getUsername();//用户名字
        List<Book> bo=bookService.selectByuname(uname);//订单信息
        model.addAttribute("boo",bo);
        return "order";

    }

}
