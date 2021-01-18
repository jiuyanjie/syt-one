package com.bookManagerment.controller;

import com.bookManagerment.service.LoginService;
import com.bookManagerment.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    //用户登录
    @PostMapping
    @ResponseBody
    public String login(@RequestBody LoginVo loginVo, HttpServletResponse response,
                                      HttpServletRequest request){
        String url = loginService.login(loginVo,response,request);
        return url;
    }

    //读者自动登录
    @GetMapping("/autoLogin")
    public String autoLoginReader(HttpServletResponse response,HttpServletRequest request){
        Boolean flag = loginService.autoLoginReader(response, request);
        String url = "";
        if(flag){   //登录成功
            url = "/pages/reader/bookDatalist.jsp";
        }else{  //登陆失败
            url = "/pages/login.jsp";
        }
        return "redirect:"+ url;
    }



}
