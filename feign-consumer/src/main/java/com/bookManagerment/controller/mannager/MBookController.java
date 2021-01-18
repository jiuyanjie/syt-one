package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.Books;
import com.bookManagerment.service.BookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/manager/books")
@RestController()
public class MBookController {

    @Autowired
    private BookService bookService;

    //分页获取图书信息 并可查找
    @GetMapping(value = "/{page}/{size}")
    public ResponseEntity<Page<Books>> booksList(@PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size,
                                                Books book){
        return ResponseEntity.ok(bookService.getBookList(page,size,book));
    }

    //添加图书
    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Books books){
        bookService.addBook(books);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //修改图书
    @PutMapping
    public ResponseEntity<Void> modifyBook(@RequestBody Books books){
        bookService.modifyBook(books);
        return ResponseEntity.ok().build();
    }

    //修改图书的状态
    @PutMapping("/{bId}/{status}")
    public ResponseEntity<Void> modifyBookStatus(@PathVariable("bId") Integer bId,
                                                 @PathVariable("status") Boolean status){
        bookService.modifyBookStatus(bId,status);
        return ResponseEntity.ok().build();
    }

    //修改图书的总数量
    @PutMapping("/total/{bId}/{total}")
    public ResponseEntity<Void> modifyBookTotal(@PathVariable("bId") Integer bId,
                                                 @PathVariable("total") Integer total){
        bookService.modifyBookTotal(bId,total);
        return ResponseEntity.ok().build();
    }

//    //删除图书 多个
    @DeleteMapping
    public ResponseEntity<Void> deleteBookBybIds(@RequestBody List<Integer> ids){
        bookService.deleteBookBybIds(ids);
        return ResponseEntity.ok().build();
    }

    //删除图书 一个
    @DeleteMapping("/{bId}")
    public ResponseEntity<Void> deleteBookBybId(@PathVariable("bId") Integer bId){
        bookService.deleteBookBybIds(Arrays.asList(bId));
        return ResponseEntity.ok().build();
    }

}
