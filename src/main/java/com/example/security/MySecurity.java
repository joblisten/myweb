package com.example.security;



import com.example.component.ExpiredSessionHander;
import com.example.component.UserFailHander;
import com.example.component.UserSueccedHander;
import com.example.service.LogintServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import javax.sql.DataSource;

/*

 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurity extends WebSecurityConfigurerAdapter {
/*
通过属性注入
 */
    @Autowired
    LogintServic logintServic;

/*
通过构造方法注入
    BlogService blogService;

    @Autowired
    public BlogService  blogService(){
       return this.blogService=blogService;
    }*/

    @Autowired
    UserSueccedHander userSueccedHander;

    @Autowired
    UserFailHander userFailHander;

    @Autowired
    ExpiredSessionHander expiredSessionHander;

    @Autowired
    DataSource  dataSource;



    @Bean
    public PersistentTokenRepository    persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//url拦截注册器
                .antMatchers("/logint","/","/register","/registert","/about","/portfolio").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/logint") //自定义表单登录页面地址
                .loginProcessingUrl("/login")//form表单POST请求url提交地址，默认为/login
                .passwordParameter("password")//form表单用户名参数名
                .usernameParameter("username") //form表单密码参数名
                .successHandler(userSueccedHander) //自定义登录成功
                .failureHandler(userFailHander)//自定义登录失败
                /*.and()
                .rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(1200)
                .userDetailsService(logintServic);*/
                .and()//结束上面标签
                .sessionManagement()//session管理
                .invalidSessionUrl("/logint")
                .maximumSessions(10)//同时最大用户
                .expiredSessionStrategy(expiredSessionHander);


        /*
        springsecurity---通过SessionManagementConfigurer配置SessionManagement的行为
        其中有SessionConfiguere，CorsConfiguere，RememberMwConfiguere都实现了SecurityConfiguere

         最终SessionManagementConfiguere在configure将最终的SessionManagementFilter插入过滤链实现会话管理

         */


        http.csrf().disable();

    }

    /*
    AuthenticationManagerBuilder：认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(logintServic).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/images/**");
    }


    //允许多请求地址多加斜杠  比如 /msg/list   //msg/list,本项目由多请求地址如：/portfolio?style=,pageNum=
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }
}
