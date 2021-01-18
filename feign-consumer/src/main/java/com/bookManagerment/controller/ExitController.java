package com.bookManagerment.controller;

import com.bookManagerment.service.ExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/exit")
@Controller
public class ExitController {

    @Autowired
    private ExitService exitService;

    @GetMapping("/reader")
    public String exitReader(HttpServletRequest request, HttpServletResponse response){
        exitService.exitReader(request,response);
        return "redirect:/pages/login.jsp";
    }

    @GetMapping("/manager")
    public String exitManager(HttpServletRequest request){
        exitService.exitManager(request);
        return "redirect:/pages/login.jsp";
    }

}
