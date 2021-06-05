package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {
    /*
    博客图片保存绝对地址
     */
    @Value("${imgaes.save.path}")
    private String filePath;

    /*
    猫咪图片保存绝对地址
     */
    @Value("${imgaes.save.pathc}")
    private String filePathc;

    /*
    狗狗图片保存绝对地址
     */
    @Value("${imgaes.save.pathd}")
    private String filePathd;

    /*
    博客映射的相对地址
     */
    @Value("${imgaes.path.relative}")
    private String filePathRe;

    /*
    增加猫咪商品
     */
    @Value("${imgaes.path.cat}")
    private String filePc;

    /*
    增加狗狗商品
     */
    @Value("${imgaes.path.dog}")
    private String filePd;

    public String imgageUp(MultipartFile file){


        /*
        文件不能为空
         */
        if(file.isEmpty()){
            System.out.println("文件为空");
            return " ";
        }
        /*
        为映射数据库和保存地址准备
         */
        String fileName=file.getOriginalFilename();//获取文件名
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取后缀名
        //String filePath="E://Idea workspace//demoweb2//src//main//resources//static//images//image//";

        fileName= UUID.randomUUID().toString().substring(0,4)+suffixName;//新图片名
        File dest=new File(filePath+fileName); //创建新文件
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();

        }
        try {
            file.transferTo(dest); //保存图片

            System.out.println("file = " + "图片保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String NewfiLiePath=filePathRe+fileName;//映射到数据库的地址

        return NewfiLiePath;
    }

    /*
    猫咪
     */
    public String imgageUpcat(MultipartFile file){


        /*
        文件不能为空
         */
        if(file.isEmpty()){
            System.out.println("文件为空");
        }
        /*
        为映射数据库和保存地址准备
         */
        String fileNamec=file.getOriginalFilename();//获取文件名
        String suffixName=fileNamec.substring(fileNamec.lastIndexOf("."));//获取后缀名
        //String filePath="E://Idea workspace//demoweb2//src//main//resources//static//images//image//";

        fileNamec= UUID.randomUUID().toString().substring(0,4)+suffixName;//新图片名
        File dest=new File(filePathc+fileNamec); //创建新文件
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();

        }
        try {
            file.transferTo(dest); //保存图片

            System.out.println("file = " + "图片保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String NewfiLiePath=filePc+fileNamec;//映射到数据库的地址

        return NewfiLiePath;
    }
    /*
   狗狗
    */
    public String imgageUpdog(MultipartFile file){


        /*
        文件不能为空
         */
        if(file.isEmpty()){
            System.out.println("文件为空");
        }
        /*
        为映射数据库和保存地址准备
         */
        String fileNamed=file.getOriginalFilename();//获取文件名
        String suffixName=fileNamed.substring(fileNamed.lastIndexOf("."));//获取后缀名
        //String filePath="E://Idea workspace//demoweb2//src//main//resources//static//images//image//";

        fileNamed= UUID.randomUUID().toString().substring(0,4)+suffixName;//新图片名
        File dest=new File(filePathd+fileNamed); //创建新文件
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();

        }
        try {
            file.transferTo(dest); //保存图片

            System.out.println("file = " + "图片保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String NewfiLiePath=filePd+fileNamed;//映射到数据库的地址

        return NewfiLiePath;
    }


}
