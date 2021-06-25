package com.example.service;

import com.example.dao.VersionInfoMapper;
import com.example.eunm.StatesEunm;
import com.example.util.DecryptUtil;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionInfoService {
    @Autowired
    VersionInfoMapper versionInfoMapper;

    /**
     * 客户端版本号
     * @return
     * @throws Exception
     */
    public ResultVo selectVersion() throws Exception {

        return new ResultVo(StatesEunm.SUCCESS.getCode(), StatesEunm.SUCCESS.getMsg(), DecryptUtil.encrypt(String.valueOf(versionInfoMapper.selectVersion())));
    }
}
