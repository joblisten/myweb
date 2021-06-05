package com.example.service;

import com.example.dao.BlogMapper;
import com.example.po.Blog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service

public class BlogService {

    @Autowired
    BlogMapper blogMapper;

    /*
    根据用户或者标题查询订单
    */

    public PageInfo<Blog> selectblog(String name,int pageNum){
        PageHelper.startPage(pageNum,15);
        List<Blog> list=blogMapper.selectblog(name);
        PageInfo<Blog> pageIn=new PageInfo<>(list);
        return pageIn;
    }

    /*
 管理员根据博客id删除博客
  */

    public int blogDe( Integer aid){
        return blogMapper.blogDe(aid);
    }

    /*
  获取全部博客
   */

    public PageInfo<Blog> selectAll(int pageNum){
        PageHelper.startPage(pageNum,8);
        List<Blog> list=blogMapper.selectAll();
        PageInfo<Blog> pageIn=new PageInfo<>(list);
        return pageIn;
    }

    /*
    随机博客
     */
    public PageInfo<Blog> selectBlog(int pageNum){
        PageHelper.startPage(pageNum,9);
        List<Blog> list=blogMapper.selectBlog();
        PageInfo<Blog> pageIn=new PageInfo<>(list);
        return pageIn;

    }

    /*
    置顶博客
     */

    public List<Blog> isTopBlog(){
        List<Blog> top=blogMapper.isTopBlog();
        return top;
    }

    /*
    博客详情页
     */
    public List<Blog> selectByAid(Integer aid){
        List<Blog> blo=blogMapper.selectByAid(aid);
        return blo;
    }

    /*
    获取自身博客
     */

    public List<Blog> selectMyblog(String aname){
        List<Blog> my=blogMapper.selectMyblog(aname);
        return my;
    }
   public int replyBlog(String name,
                  Integer bid, Date bdate,String content
    ){
        return blogMapper.replyBlog(name,bid,bdate,content);
   }



    /*
    写博客
     */

    public int setBlog( String aname, String title,
                       String introduction,  String content,
                        Date adate,  String aimages,String istop){

        int set=blogMapper.setBlog(aname,title,introduction,content,adate,aimages,istop);
        return set;
    }

    /*
    浏览量
     */

    public void  upview(Integer aid,Integer pageview){
        blogMapper.upView(aid,pageview);
    }
    /*
   根据id获取博客浏览量
    */
    public int gView(Integer aid){
        int pageview=blogMapper.gView(aid);
        return pageview;
    }
}
