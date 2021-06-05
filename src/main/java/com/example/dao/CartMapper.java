package com.example.dao;

import com.example.po.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface CartMapper {
    /*
    加入购物车
     */
    @Insert("INSERT INTO cart(pid,pname,uname,url,num,price,total) VALUES (#{pid},#{pname},#{uname},#{url},#{num},#{price},#{total})")
    int setGoods(@Param("pid") Integer pid, @Param("pname") String pname,
                 @Param("uname") String uname,
                @Param("url") String url, @Param("num") Integer num,
                @Param("price") double price,@Param("total") double total);

    @Select("SELECT * FROM cart WHERE uname = #{uname} ORDER BY id desc ")
    List<Cart> selectByName(String uname);


    @Select("SELECT * FROM cart WHERE id = #{id} ")
    List<Cart> selectById(Integer id);


    @Select("SELECT * FROM cart WHERE id = #{id} ")
    Cart selectAll(Integer id);



    /*
    购物车修改时,根据用户名和商品名，修改数量
     */
    @Update("UPDATE cart SET num = #{num},total = #{total}  WHERE  id = #{id}")
    int updateCart(@Param("id") Integer id,@Param("num") Integer num,@Param("total") double total);


    /*
    根据购物车订单id删除商品
     */
    @Delete("DELETE FROM cart WHERE id = #{id}")
    int delectCart(Integer id);



}
