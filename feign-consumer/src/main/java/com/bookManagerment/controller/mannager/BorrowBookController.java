package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.service.BorrowBookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("manager/borrowBook")
@RestController
public class BorrowBookController {

    @Autowired
    private BorrowBookService borrowBookService;

    //分页查询 并可以带查询参数
   @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<BorrowBooks>> borrowBookByPage(@PathVariable("page") Integer page,
                                                              @PathVariable("size") Integer size,
                                                              BorrowBooks borrowBooks){
       return ResponseEntity.ok(borrowBookService.borrowBookByPage(page,size, borrowBooks,false));
   }

}
