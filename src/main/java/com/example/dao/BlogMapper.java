package com.example.dao;

import com.example.po.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface BlogMapper {

    /*
根据用户或者标题查询博客
 */
    @Select("SELECT * FROM blog WHERE aname = #{name} or title = #{name} ")
    List<Blog> selectblog(String name);

    /*
    评论博客
     */
    @Insert("INSERT INTO commentary(name,bid,bdate,content) VALUES (#{name},#{bid},#{bdate},#{content})")
    int replyBlog(@Param("name") String name,
                @Param("bid") Integer bid, @Param("bdate") Date bdate, @Param("content") String content
                );

    /*
    写博客
     */
    @Insert("INSERT INTO blog(aname,title,introduction,content,adate,aimages,istop,pageview) VALUES (#{aname},#{title},#{introduction},#{content},#{adate},#{aimages},#{istop},0)")
    int setBlog(@Param("aname") String aname, @Param("title") String title,
                @Param("introduction") String introduction, @Param("content") String content,
                @Param("adate") Date adate, @Param("aimages") String aimages,@Param("istop") String istop);

    /*
    随机博客
     */
    @Select("SELECT * FROM blog  WHERE aid >= ((SELECT MAX(aid) FROM blog)-(SELECT MIN(aid) FROM blog)) * RAND() + (SELECT MIN(aid) FROM blog) AND istop=0")
    List<Blog> selectBlog();

    /*
    置顶博客
     */

    @Select("SELECT * FROM blog WHERE istop = 1 ORDER BY adate DESC  LIMIT 2")
    List<Blog> isTopBlog();

    /*
    博客详情页
     */
    @Select("SELECT * FROM blog WHERE aid=#{aid}")
    List<Blog> selectByAid(Integer aid);

    /*
    获取自身博客
     */
    @Select("SELECT * FROM blog WHERE aname=#{aname} order by aid desc")
    List<Blog> selectMyblog(String aname);

    /*
    浏览量
     */
    @Update("UPDATE blog SET pageview=#{pageview} where aid=#{aid}")
    void upView(Integer aid,Integer pageview);

    /*
    根据id获取博客浏览量
     */
    @Select("SELECT pageview FROM blog WHERE aid=#{aid}")
    int gView(Integer aid);

    /*
    获取全部博客
     */
    @Select("SELECT * FROM blog ")
    List<Blog> selectAll();

    /*
  管理员根据博客id删除博客
   */
    @Delete("DELETE FROM blog WHERE aid = #{aid}")
    int blogDe( Integer aid);
}



