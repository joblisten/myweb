package com.example.service;

import com.example.dao.CommentaryMapper;
import com.example.po.Commentary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CommentaryService {

    @Autowired
    @Resource
    CommentaryMapper  commentaryMapper;
    public List<Commentary> selectByid(Integer id){
        return commentaryMapper.selectByid(id);
    }

}
