package com.example.service;

import com.example.dao.CatMapper;


import com.example.po.Cat;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/*
猫咪匹配
 */
@Slf4j

@Service
public class CatService {


    @Autowired
    CatMapper catMapper;

    /*
   添加猫咪商品
    */
    @Transactional()
    public int settCat( String name, String url, double price, String style){
        return catMapper.settCat(name,url,price,style);
    }

    /*
    星座匹配
     */
    public List<Cat> selectBycname(String cname){
        return catMapper.selectBycname(cname);
    }


    /*
    幸运匹配
     */

    public List<Cat> randomS(){

        List<Cat> catRandom=catMapper.randomSelect();
        log.info("第一次调用");
        return catRandom;
    }
    /*
    未被领养的猫咪
     */

    public PageInfo<Cat> selectDcat(int pageNum){
        PageHelper.startPage(pageNum,8); //当前页数和数目
        List<Cat> dcat=catMapper.selectDcat();
        PageInfo<Cat> pageInfo= new PageInfo<Cat>(dcat);
        log.info("第一次调用");
        return pageInfo;
    }


    /*
    买猫咪详情页
     */
    public List<Cat> selectById(int id){
        List<Cat> catid=catMapper.selectById(id);
        return catid;

    }
    /*
   管理员修改猫咪价格
    */
    public int upCat(Integer id,Double price){
        return catMapper.upCat(id,price);
    }


}
