package com.example.config;


import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;

/**
 * redis配置
 * (1)缓存管理器：redis缓存管理器---（lettuce连接池工厂,redis默认缓存配置）
 * (2)lettuce连接池工厂：1.设置redis模式（单机,哨兵,集群）;2.设置主机,端口,密码,数据库索引;3.加载模式和自定义lettuce连接池
 * (3)lettuce连接池:设置数据库连接属性,
 * (4)自定义redisTemplate序列化和反序列化,设置连接工厂,自定义缓存key生成策略
 */
@Slf4j
@Configuration
public class RedisConfig {

    /**
     * Redis服务器地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * Redis服务器连接端口
     */
    @Value("${spring.redis.port}")
    private Integer port;

    /**
     * Redis数据库索引（默认为0）
     */
    @Value("${spring.redis.database}")
    private Integer database;

    /**
     * Redis服务器连接密码（默认为空）
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 连接超时时间（毫秒）
     */
    @Value("${spring.redis.timeout}")
    private Integer timeout;

    /**
     * 连接池最大连接数（使用负值表示没有限制）
     */
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxTotal;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Integer maxWait;

    /**
     * 连接池中的最大空闲连接
     */
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;

    /**
     * 连接池中的最小空闲连接
     */
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    /**
     * 关闭超时时间
      */
    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private Integer shutdown;

    /**
     * Lettuce连接池
     * @return
     */
    @Bean
    public LettucePoolingClientConfiguration lettucePoolingClientConfiguration(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //连接池中的最大空闲连接
        config.setMaxIdle(maxIdle);
        //连接池中的最小空闲连接
        config.setMinIdle(minIdle);
        //最大连接数
        config.setMaxTotal(maxTotal);
        //连接池最大阻塞等待时间
        config.setMaxWaitMillis(maxWait);

        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder().poolConfig(config)
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(shutdown))
                .build();
        return pool;
    }

    /**
     * 自定义redisTemplate序列化和反序列化
     * @return
     */

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate<Object, Serializable> redisTemplate = new RedisTemplate<>();

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        //设置键key的序列化<K,V>
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置值value的序列化
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory());
        // 开启事务
       // redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

    /**
     * redis--lettuce连接池工厂
     * @return
     */
    @Bean
    public RedisConnectionFactory factory(){
        //单机模式
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(database);
        configuration.setPassword(RedisPassword.of(password));
        LettuceConnectionFactory lettuceConnectionFactory =
                new LettuceConnectionFactory(configuration,lettucePoolingClientConfiguration());
        //哨兵模式
        //RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
        //集群模式
        //RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();

        return lettuceConnectionFactory;
    }

    @Bean
    public CacheManager cacheManager(){
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager redisCacheManager = new RedisCacheManager(writer,redisCacheConfiguration);
        return redisCacheManager;


    }

    /**
     * 自定义缓存key生成策略
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj:params){
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }



}
