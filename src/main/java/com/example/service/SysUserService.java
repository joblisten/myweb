package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.SysUserMapper;
import com.example.eunm.StatesEunm;
import com.example.po.SysUser;
import com.example.util.DecryptUtil;
import com.example.util.RedisUtil;
import com.example.vo.ResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisUtil redisUtil;


    public static String EntityJSON = "";
    public static String EntityArray = "";

    static {
        try {
            EntityJSON = DecryptUtil.encrypt(new JSONObject());
            EntityArray = DecryptUtil.encryptToArray(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param obj
     * @return
     */
    public ResultVo login(JSONObject obj) throws Exception {
        ResultVo resultVo = new ResultVo();

        //解密
        JSONObject jsonObject = DecryptUtil.decry(obj.getString("detail"));

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        SysUser sysUser = sysUserMapper.selectByName(username);

        if (sysUser != null && password.equals(sysUser.getPassword())) {
            //用户信息加密,返回
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                    DecryptUtil.encrypt(sysUser));

            log.info("用户={}登录成功", username);
            return resultVo;
        }

        //登录失败
        resultVo.setAll(StatesEunm.FAIL.getCode(), StatesEunm.FAIL.getMsg(), EntityJSON);
        log.info("用户={}登录失败", username);
        return resultVo;
    }

    /**
     * 游客/用户注册
     *
     * @param obj
     * @return
     * @throws Exception
     */

    public ResultVo register(JSONObject obj) throws Exception {
        ResultVo resultVo = new ResultVo();

        //解密
        JSONObject jsonObject = DecryptUtil.decry(obj.getString("detail"));

        Integer type = jsonObject.getInteger("type");

        //用户注册
        if (type == null) {
            type = 1;
            log.info("type={}", type);
        }
        //游客注册
        if (type == 0) {
            log.info("type={}", type);
            String random = UUID.randomUUID().toString();
            //游客密码加密
            int row = sysUserMapper.insertUser(random, DecryptUtil.encrypt(random));
            if (row > 0) {
                //游客信息加密返回
                resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                        DecryptUtil.encrypt(sysUserMapper.selectByName(random)));
                log.info("游客={}注册成功", random);
                return resultVo;

            }
            //游客注册失败
            resultVo.setAll(StatesEunm.FAIL.getCode(), StatesEunm.FAIL.getMsg(), EntityJSON);
            log.info("游客={}注册失败", random);
            return resultVo;

        }

        //用户注册
        String username = jsonObject.getString("username");
        log.info("username={}", username);
        //获取前端加密后的密码
        String password = jsonObject.getString("password");


        //不允许同名用户
        if (sysUserMapper.selectByName(username) != null) {
            resultVo.setAll(StatesEunm.NAME_EXSIT.getCode(), StatesEunm.NAME_EXSIT.getMsg(), EntityJSON);
            log.info("用户={}已经存在", username);
            return resultVo;
        }

        //传输的密码已经是加密的密码,直接保存
        if (sysUserMapper.insertUser(username, password) > 0) {

            //用户信息加密
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                    DecryptUtil.encrypt(sysUserMapper.selectByName(username)));

            log.info("用户={}注册成功", username);
            return resultVo;
        }

        resultVo.setAll(StatesEunm.FAIL.getCode(), StatesEunm.FAIL.getMsg(), EntityJSON);
        log.info("用户={}注册失败", username);
        return resultVo;
    }


    /**
     * 荣誉排行
     *
     * @param obj
     * @return
     */

    public ResultVo selectHonor(JSONObject obj) throws Exception {
        //解密
        String detail = obj.getString("detail");
        JSONObject jsonObject = DecryptUtil.decry(detail);

        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        log.info("pageNum={}", pageNum);
        log.info("pageSize={}", pageSize);

        ResultVo resultVo = new ResultVo();
        //缓存有数据
        if (redisUtil.hasKey(detail)) {
            log.info("从缓存取出数据");

            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                    redisUtil.get(detail));
            log.info("re={}", redisUtil.getExpire(detail, TimeUnit.SECONDS));
            return resultVo;
        }
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.selectHonor();
        PageInfo pageInfo = new PageInfo(sysUserList);

        if (pageNum > pageInfo.getPages()) {
            log.info("获取分页失败,页数={}太大", pageNum);
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(), EntityArray);
            return resultVo;
        }

        resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(), DecryptUtil.encryptToArray(sysUserList));
        redisUtil.set(detail, DecryptUtil.encryptToArray(sysUserList), getSecondsNextEarlyMorning());
        log.info("re={}", resultVo);
        return resultVo;

    }

    /***
     * 根据id获取用户状态
     * @param obj
     * @return
     * @throws Exception
     */
    public ResultVo selectById(JSONObject obj) throws Exception {
        ResultVo resultVo = new ResultVo();
        String id = String.valueOf(obj.getInteger("detail"));
        //解密---{“id”:"1111"}
        JSONObject decry = DecryptUtil.decry(id);
        if (decry == null) {
            log.info("解密失败{}", id);
            resultVo.setAll(StatesEunm.FAIL.getCode(), StatesEunm.FAIL.getMsg(), EntityJSON);
            return null;
        }

        log.info("根据id获取成功{}", id);
        try {
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(),
                    DecryptUtil.encrypt(String.valueOf(sysUserMapper.selectById
                            (decry.getInteger("id")))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVo;


    }

    public ResultVo changeHonor(JSONObject obj) {
        ResultVo resultVo = new ResultVo();
        JSONObject detail = DecryptUtil.decry(obj.getString("detail"));
        Integer id = detail.getInteger("id");
        Integer honor = detail.getInteger("honor");

        log.info("id={}",id);
        int row = sysUserMapper.changeHonorById(id, honor);

        if (row > 0) {
            log.info("id={}更新成功", id);
            resultVo.setAll(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(), EntityJSON);
            return resultVo;
        }
        log.info("id={}更新失败", id);
        resultVo.setAll(StatesEunm.FAIL.getCode(), StatesEunm.FAIL.getMsg(), EntityJSON);
        return resultVo;


    }


    /**
     * 返回当天凌晨时间秒数
     *
     * @return
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        //增长一天的时间
        cal.add(Calendar.DAY_OF_YEAR, 1);
        //以一天24小时
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //毫秒%1000
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }


}
