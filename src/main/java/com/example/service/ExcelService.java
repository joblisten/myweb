package com.example.service;

import com.example.dao.BookMapper;
import com.example.po.Book;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    BookMapper bookMapper;
    /*
    数据库订单导出到excel
     */
    public HSSFWorkbook show() {
        List<Book> list=bookMapper.finAll();
        HSSFWorkbook workbook=new HSSFWorkbook(); //新建excel表
        HSSFSheet sheet =workbook.createSheet("订单");//excel名字
        int rowNum = 1;
        String[] headers = { "编号",  "订单号","用户", "收件人", "商品", "数量", "总价", "联系方式", "收件地址", "备注",  "日期", "状态"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //在表中存放查询到的数据放入对应的列
        for (Book book : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(book.getId());
            row1.createCell(1).setCellValue(book.getOnumber());
            row1.createCell(2).setCellValue(book.getUname());
            row1.createCell(3).setCellValue(book.getAname());
            row1.createCell(4).setCellValue(book.getPname());
            row1.createCell(5).setCellValue(book.getNum());
            row1.createCell(6).setCellValue(book.getTotal());
            row1.createCell(7).setCellValue(book.getNumber());
            row1.createCell(8).setCellValue(book.getAddress());
            row1.createCell(9).setCellValue(book.getOther());
            row1.createCell(10).setCellValue(df.format(book.getBdate()));
            row1.createCell(11).setCellValue(book.getDealw());
            rowNum++;
        }
        return workbook;
    }

}
