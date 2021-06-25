package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.concurrent.TimeUnit;
@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * key是否存在
     * @param key
     * @return
     */
   public boolean hasKey(String key){
       try {
           return redisTemplate.hasKey(key);
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }

   }

    /**
     * 根据key获取缓存
     * @param key
     * @return
     */
   public Object get(String key){
       return key==null?null:redisTemplate.opsForValue().get(key);
   }

    /**
     * 缓存放入
     * @param key
     * @param value
     * @return
     */
   public boolean set(String key,Object value){
       try {

           redisTemplate.opsForValue().set(key,value);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }

    /**
     * 放入缓存,并设置缓存时间
     * @param key
     * @param value
     * @param time
     * @return
     */
   public boolean set(String key,Object value ,long time){
       try {

           if(time>0){
           redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
           }
           else{
               set(key,value);
           }
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }

    /**
     * 根据key指定缓存时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @param seconds
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key, TimeUnit seconds) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     * @param key key可以传一个或者多个
     */
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if ((key.length==1)){
                redisTemplate.delete(key[0]);
            }
            else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}


