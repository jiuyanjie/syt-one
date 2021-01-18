package com.bookManagerment.controller;

import com.bookManagerment.entity.Reader;
import com.bookManagerment.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("register")
@RestController
public class RegisterController {

    @Autowired
    private ReaderService readerService;

    //用户名是否存在
    @PostMapping("/account/{accountName}")
    @ResponseBody
    public ResponseEntity<Void> accountName(@PathVariable("accountName") String accountName){
        readerService.accountNameExist(accountName);
        return ResponseEntity.ok().build();
    }

    //用户注册
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> registerReader(@RequestBody Reader reader){
        reader.builderReader();
        readerService.saveReader(reader);
        return ResponseEntity.ok().build();
    }

    //发送邮箱验证码
    @PostMapping("/verifyMailCode/{account}/{email}")
    @ResponseBody
    public ResponseEntity<Void> verifyMailCode(@PathVariable("account") String account,
                                               @PathVariable("email") String email){
        readerService.verifyMailCode(account,email);
        return ResponseEntity.ok().build();
    }

}
