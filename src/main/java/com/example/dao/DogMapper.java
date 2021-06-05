package com.example.dao;




import com.example.po.Dog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DogMapper {

     /*
     猫咪星座匹配,根据星座名
     */

    @Select("SELECT * FROM dog WHERE cname = #{cname} ")
    List<Dog> selectByd(@Param("cname") String cname);


    /*
   随机匹配，三条条记录
    */
    @Select("SELECT * FROM dog  WHERE id >= ((SELECT MAX(id) FROM dog)-(SELECT MIN(id) FROM dog)) * RAND() + (SELECT MIN(id) FROM dog)  LIMIT 3")
    List<Dog> randomSd();
    /*
  未被领养的狗狗
   */
    @Select("SELECT * FROM dog ")
    List<Dog> selectDog();


    /*
    狗狗详情
     */
    @Select("SELECT * FROM dog WHERE id = #{id}")
    List<Dog> selectById(Integer id);

    /*
   管理员修改狗狗价格
    */
    @Update("UPDATE dog SET price = #{price}  WHERE  id = #{id}")
    int upDog(@Param("id")Integer id,@Param("price")Double price);

    /*
   添加狗狗商品
    */
    @Insert("INSERT INTO dog (name,url,price,style)  VALUES (#{name},#{url},#{price},#{style})")
    int settDog(@Param("name") String name,@Param("url") String url,@Param("price") double price,@Param("style") String style);


}
