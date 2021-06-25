package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.eunm.OptEunm;
import com.example.eunm.StatesEunm;
import com.example.service.SysUserService;
import com.example.util.DecryptUtil;
import com.example.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


/**
 * @author xdb
 * @date 2021/6/7
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    SysUserService userService;


    /**
     * opt:0,1:login,register
     * @param obj
     * @param opt
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/gateway",produces={"application/json"})
    public ResultVo getWay(@RequestBody JSONObject obj,
                           @RequestParam("opt") Integer opt) throws Exception {

        log.info("opt={}", opt);
        if(opt== OptEunm.LOGIN.getCode()){
            return userService.login(obj);
        }
        if (opt==OptEunm.REGISTER.getCode()){
        return userService.register(obj);}

        if(opt==OptEunm.GET_PAGE_HONOR.getCode()){
        return userService.selectHonor(obj);}

        return  userService.changeHonor(obj);

    }

    /**
     * 根据用户id，查询用户状态
     * @param obj
     * @return
     */
    @GetMapping(value = "/get_auth")
    public ResultVo getAuth(@RequestBody JSONObject obj) throws Exception {

        return userService.selectById(obj);
    }



    /**
     * 当前时间的时间戳
     * @return
     */
    @PostMapping(value = "/get_server_time")
    public ResultVo timeStamp() {
        ResultVo resultVo = new ResultVo();
        try {
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                    DecryptUtil.encrypt(String.valueOf(System.currentTimeMillis())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVo;
    }


    /**
     * 测试socket
     * @return
     * @throws IOException
     */
    @GetMapping("/home")
    public String page() throws IOException {
        return "index";
    }

}
