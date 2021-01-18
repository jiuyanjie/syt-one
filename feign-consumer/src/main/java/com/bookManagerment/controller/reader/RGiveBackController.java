package com.bookManagerment.controller.reader;


import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.GiveBackBooks;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.service.GiveBackBookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("reader/giveBack")
@RestController
public class RGiveBackController {


    @Autowired
    private GiveBackBookService giveBackBookService;

    @Autowired
    private BMSystemProperties bmProperties;

    //分页查询 并可以带查询参数
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<GiveBackBooks>> giveBackBook(@PathVariable("page") Integer page,
                                                            @PathVariable("size") Integer size,
                                                            GiveBackBooks reserveGiveBack,
                                                            HttpSession session){
        Reader reader = (Reader) session.getAttribute(bmProperties.getReaderSessionName());
        reserveGiveBack.setRName(reader.getRName());
        return ResponseEntity.ok(giveBackBookService.giveBackBook(page,size, reserveGiveBack,true));
    }

}
