package com.example.service;

import com.example.dao.BookMapper;
import com.example.po.Book;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookMapper bookMapper;
    /*
    一键受理
     */
    public  int upAlldealw(String dealw){
        return bookMapper.upAlldealw(dealw);
    }

    public PageInfo selectDeal(String dealw,int pageNum){
        PageHelper.startPage(pageNum,10);
        List<Book> list=bookMapper.selectDeal(dealw);
        PageInfo<Book> pageIn=new PageInfo<>(list);
        return pageIn;}
    /*
     单个订单受理
      */

    public int upDealw(Integer id,String dealw){
        return bookMapper.upDealw(id,dealw);
    }

    /*
     根据订单号或者用户名查询订单
     */
    public PageInfo<Book> selectse(String name,int pageNum){
        PageHelper.startPage(pageNum,15);
        List<Book> list=bookMapper.selectse(name);
        PageInfo<Book> pageIn=new PageInfo<>(list);
        return pageIn;
    }

    /*
    管理员管理订单
     */

    public PageInfo<Book> finAll(int pageNum){
        PageHelper.startPage(pageNum,15);
        List<Book> list=bookMapper.finAll();
        PageInfo<Book> pageIn=new PageInfo<>(list);
        return pageIn;
    }


    /*
    生成订单
     */
    public int setOrder(String uname, String aname,
                        String pname, Integer num, double total,
                        String number, String address,
                        String other, String sta, String url, String onumber, Date bdate,String dealw){
        return bookMapper.setOrder(uname,aname,pname,num,total,number,address,other,sta,url,onumber, bdate,dealw);
    }

    public List<Book> selectByuname( String uname){
        return bookMapper.selectByuname(uname);
    }
}
