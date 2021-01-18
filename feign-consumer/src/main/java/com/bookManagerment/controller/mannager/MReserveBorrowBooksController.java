package com.bookManagerment.controller.mannager;

import com.bookManagerment.entity.ReserveBorrowBooks;
import com.bookManagerment.service.ReserveBorrowBooksService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("manager/reserveBorrow")
@RestController
public class MReserveBorrowBooksController {

    @Autowired
    private ReserveBorrowBooksService reserveBookService;

    //分页查询 并可以带查询参数
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ReserveBorrowBooks>> borrowBookByPage(@PathVariable("page") Integer page,
                                                                     @PathVariable("size") Integer size,
                                                                     ReserveBorrowBooks reserveBorrowBooks){
        return ResponseEntity.ok(reserveBookService.reserveBookByPage(page,size, reserveBorrowBooks));
    }

    //借书 一个
    @PostMapping("/{resId}")
    public ResponseEntity<Void> saveReserve(@PathVariable("resId") Integer resId){
        reserveBookService.saveReserves(Arrays.asList(resId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //借书 多个
    @PostMapping
    public ResponseEntity<Void> saveReserves(@RequestBody List<Integer> resIds){
        reserveBookService.saveReserves(resIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
