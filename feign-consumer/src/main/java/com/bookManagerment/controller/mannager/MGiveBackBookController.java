package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.GiveBackBooks;
import com.bookManagerment.service.GiveBackBookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("manager/giveBack")
@RestController
public class MGiveBackBookController {

    @Autowired
    private GiveBackBookService giveBackBookService;

    //根据 bbId 还书
    @PostMapping("/{bbId}")
    public ResponseEntity<GiveBackBooks> giveBackBookById(@PathVariable("bbId") Integer bbId){
        return ResponseEntity.ok().body(giveBackBookService.giveBackBookById(bbId));
    }

    //分页查询 并可以带查询参数
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<GiveBackBooks>> giveBackBook(@PathVariable("page") Integer page,
                                                            @PathVariable("size") Integer size,
                                                            GiveBackBooks reserveGiveBack){
        return ResponseEntity.ok(giveBackBookService.giveBackBook(page,size, reserveGiveBack,false));
    }
}
