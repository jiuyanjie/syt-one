package com.bookManagerment.controller.reader;


import com.bookManagerment.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("reader")
@Controller
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    //发送支付请求
    @PostMapping("/balance")
    public void addBalance(Double money,HttpServletRequest request, HttpServletResponse response){
        readerService.addBalance(money,request,response);
    }

    //支付同步通知
    @RequestMapping("/returnUrl")
    public String returnUrl(HttpServletRequest request,HttpServletResponse response){
        readerService.returnUrl(request);
        return "redirect:/pages/reader/bookDatalist.jsp";
    }

    //支付异步通知
    @PostMapping("/notify")
    public void notify(HttpServletRequest request,HttpServletResponse response){
        readerService.notify(request,response);
    }

    @GetMapping("/refreshMoney")
    public ResponseEntity<Double> refreshMoney(HttpServletRequest request){
        return ResponseEntity.ok(readerService.refreshMoney(request));
    }

}
