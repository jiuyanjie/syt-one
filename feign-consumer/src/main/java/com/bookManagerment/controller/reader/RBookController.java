package com.bookManagerment.controller.reader;


import com.bookManagerment.entity.Books;
import com.bookManagerment.service.BookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reader/books")
@RestController()
public class RBookController {

    @Autowired
    private BookService bookService;

    //分页获取图书信息 并可查找
    @GetMapping(value = "/{page}/{size}")
    public ResponseEntity<Page<Books>> booksList(@PathVariable("page") Integer page,
                                                 @PathVariable("size") Integer size,
                                                 Books book){
        return ResponseEntity.ok(bookService.getBookListStatusTrue(page,size,book));
    }



}
