package com.example.service;

import com.example.dao.DogMapper;
import com.example.po.Dog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class DogService {

    @Autowired
    DogMapper dogMapper;
    /*
    星座匹配
     */
    public List<Dog> selectByd( String cname){
        return dogMapper.selectByd(cname);
    }

    /*
   幸运匹配
     */
    public List<Dog> randomSd(){
        return dogMapper.randomSd();
    }

    public PageInfo<Dog> selectDog(int pageNum){
        PageHelper.startPage(pageNum,8); //当前页数和数目
        List<Dog> dog=dogMapper.selectDog();
        PageInfo<Dog> pageInfo= new PageInfo<Dog>(dog);
        log.info("第一次调用");
        return pageInfo;
    }

    public List<Dog> selectById(Integer id){
        List<Dog> dogg=dogMapper.selectById(id);
        return dogg;

    }
    /*
  管理员修改狗狗价格
   */
    public int upDog(Integer id,Double price){
        return dogMapper.upDog(id,price);
    }

    /*
   添加狗狗商品
    */
 @Transactional()
    public int settDog( String name, String url, double price, String style){
        return dogMapper.settDog(name,url,price,style);
    }




}
