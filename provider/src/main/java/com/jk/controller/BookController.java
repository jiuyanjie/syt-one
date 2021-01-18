package com.jk.controller;

import com.jk.dto.AppointExecution;
import com.jk.pojo.Appointment;
import com.jk.pojo.BookBean;
import com.jk.pojo.StudentBean;
import com.jk.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("book2")
public class BookController {
    @Autowired
    private BookService bookService;

    //获取图书列表
    @RequestMapping(value="/list")
    @ResponseBody
    private List List(){
        return bookService.getList();
    }

    //搜索是否有某图书
    @RequestMapping(value = "getSomeList")
    @ResponseBody
    private List<BookBean>getSomeList(@RequestBody String name){
        return bookService.getSomeList(name);
    }

    //查看某图书的详细情况
    @RequestMapping(value = "getById")
    @ResponseBody
    private BookBean getById(@RequestBody Long bookId){
        return bookService.getById(bookId);
    }

    //验证输入的用户名、密码是否正确
    @RequestMapping(value = "validateStu")
    @ResponseBody
    private StudentBean validateStu(@RequestBody Long studentId,@RequestBody Long password){
        return bookService.validateStu(studentId,password);
    }

    @RequestMapping(value = "appoint")
    @ResponseBody
    private AppointExecution appoint(@RequestBody Long bookId,@RequestBody Long studentId){
        return bookService.appoint(bookId,studentId);
    }

    @RequestMapping(value = "getAppointByStu")
    @ResponseBody
    private List<Appointment> getAppointByStu(@RequestBody long studentId){
        return bookService.getAppointByStu(studentId);
    }
}
