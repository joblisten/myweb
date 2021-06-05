package com.example.service;

import com.example.dao.CartMapper;
import com.example.po.Cart;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;

   /*
   商品信息，生成订单要用
    */
   public  Cart selectAll(Integer id){
      return cartMapper.selectAll(id);
   }

    /*
    删除
     */
    public int delectCart(Integer id){
        return cartMapper.delectCart(id);
    }
    /*
    修改购物车
     */
    @Transactional
    public int updateCart( int id,Integer num,double total){
        return cartMapper.updateCart(id,num,total);
    }
     /*
     加入购物车
      */
     @Transactional
    public  int setGoods(Integer pid, String pname,String uname,
                        String url, Integer num, double price,double total){
        return cartMapper.setGoods(pid,pname,uname,url,num,price,total);
    }

    public PageInfo selectByName(int pageNum,String uname){
        PageHelper.startPage(pageNum,4);
        List<Cart> list=cartMapper.selectByName(uname);
        PageInfo<Cart> pageIn=new PageInfo<>(list);
        return pageIn;
    }

    /*
    单个支付
     */
    public  List<Cart> selectById(Integer id){

        return cartMapper.selectById(id);
    }




}
