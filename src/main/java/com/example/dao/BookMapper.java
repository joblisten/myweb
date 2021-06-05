package com.example.dao;

import com.example.po.Book;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface BookMapper {



    /*
    一键受理
     */
    @Update("UPDATE book SET dealw = #{dealw}")
    int upAlldealw(String dealw);

    /*
    未处理订单
     */
    @Select("SELECT * FROM book WHERE dealw = #{dealw} ")
    List<Book> selectDeal(String dealw);

    /*
    单个订单受理
     */
    @Update("UPDATE book SET dealw = #{dealw} WHERE id = #{id}")
    int upDealw(Integer id,String dealw);
    /*
  生成订单
   */
    @Insert("INSERT INTO book (uname,aname,pname,num,total,number,address,other,sta,url,onumber,bdate,dealw) VALUES (#{uname},#{aname},#{pname},#{num},#{total},#{number},#{address},#{other},#{sta},#{url},#{onumber},#{bdate},#{dealw})")
    int setOrder(@Param("uname") String uname, @Param("aname") String aname,
                 @Param("pname") String pname, @Param("num") Integer num, @Param("total") double total,
                 @Param("number") String number, @Param("address") String address,
                 @Param("other") String other, @Param("sta") String sta, @Param("url") String url, @Param("onumber") String onumber, @Param("bdate") Date bdate,@Param("dealw") String dealw);


    /*
    显示订单
     */
    @Select("SELECT * FROM book WHERE uname = #{uname} ORDER BY bdate DESC ")
    List<Book> selectByuname(@Param("uname") String uname);


    /*
    管理员管理订单,   数据库订单导出到excel
     */
    @Select("SELECT * FROM book ORDER BY bdate DESC")
    List<Book> finAll();

    /*
  根据订单号或者用户名查询订单
   */
    @Select("SELECT * FROM book WHERE uname = #{name} or onumber = #{name} or dealw=#{name}")
    List<Book> selectse(String name);

}
