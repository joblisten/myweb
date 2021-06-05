package com;


import com.example.dao.TestMapper;
import com.example.po.Testpo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MywebApplicationTests {


    @Autowired
   TestMapper testMapper ;
    @Test
    void contextLoads() {


        Testpo testpo = new Testpo();
        testpo.setName("中午");
        testpo.setSex("男");
        testMapper.insert(testpo);



    }

}
