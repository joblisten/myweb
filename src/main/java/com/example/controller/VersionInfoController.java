package com.example.controller;


import com.example.service.VersionInfoService;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionInfoController {
    @Autowired
    VersionInfoService versionInfoService;
    public ResultVo selectVersion() throws Exception {



        return versionInfoService.selectVersion();

    }
}
