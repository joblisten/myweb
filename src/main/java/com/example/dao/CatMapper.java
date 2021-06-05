package com.example.dao;

import com.example.po.Cat;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CatMapper {

    /*
    随机匹配，三条条记录
     */
    @Select("SELECT * FROM cat  WHERE id >= ((SELECT MAX(id) FROM cat)-(SELECT MIN(id) FROM cat)) * RAND() + (SELECT MIN(id) FROM cat)  LIMIT 3")
    List<Cat> randomSelect();

    /*
     猫咪星座匹配,根据星座名
     */

    @Select("SELECT * FROM cat WHERE cname = #{cname} ")
    List<Cat> selectBycname(@Param("cname") String cname);

    /*
    未被领养的猫咪
     */
    @Select("SELECT * FROM cat ")
    List<Cat> selectDcat();


    /*
    猫咪详情
     */
    @Select("SELECT * FROM cat WHERE id = #{id}")
    List<Cat> selectById(Integer id);

    /*
    管理员修改猫咪价格
     */
    @Update("UPDATE cat SET price = #{price}  WHERE  id = #{id}")
    int upCat(@Param("id")Integer id,@Param("price")Double price);

    /*
  添加猫咪商品
   */
    @Insert("INSERT INTO cat (name,url,price,style)  VALUES (#{name},#{url},#{price},#{style})")
    int settCat(@Param("name") String name,@Param("url") String url,@Param("price") double price,@Param("style") String style);




}
