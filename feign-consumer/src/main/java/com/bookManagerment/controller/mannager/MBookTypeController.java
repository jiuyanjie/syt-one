package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.BookType;
import com.bookManagerment.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("manager/bookType")
@RestController
public class MBookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    //获取图书分类
    @RequestMapping
    public ResponseEntity<List<BookType>> bookTypeList(){
        return ResponseEntity.ok(bookTypeService.bookTypeList());
    }

    //获取图书分类
    @RequestMapping("/all")
    public ResponseEntity<List<BookType>> bookTypeListAll(){
        return ResponseEntity.ok(bookTypeService.bookTypeListAll());
    }

    //添加图书分类
    @PostMapping("/{tName}")
    public ResponseEntity<Void> addBookType(@PathVariable("tName") String tName){
        bookTypeService.addBookType(tName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //删除多个图书分类
    @DeleteMapping
    public ResponseEntity<Void> deleteBookTypes(@RequestBody List<Integer> ids ){
        bookTypeService.deleteBookTypes(ids);
        return ResponseEntity.ok().build();
    }

    //删除一个图书分类
    @DeleteMapping("/{btId}")
    public ResponseEntity<Void> deleteBookType(@PathVariable("btId") Integer btId ){
        bookTypeService.deleteBookTypes(Arrays.asList(btId));
        return ResponseEntity.ok().build();
    }

    //修改一个图书分类
    @PutMapping
    public ResponseEntity<Void> modifyBookType(@RequestBody BookType bookType){
        bookTypeService.modifyBookType(bookType);
        return ResponseEntity.ok().build();
    }
}
