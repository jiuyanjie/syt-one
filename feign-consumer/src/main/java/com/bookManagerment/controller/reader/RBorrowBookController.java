package com.bookManagerment.controller.reader;

import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.service.BorrowBookService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/reader/borrowBook")
@RestController
public class RBorrowBookController {

    @Autowired
    private BorrowBookService borrowBookService;

    @Autowired
    private BMSystemProperties bmProperties;

    //分页查询 并可以带查询参数
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<BorrowBooks>> borrowBookByPage(@PathVariable("page") Integer page,
                                                              @PathVariable("size") Integer size,
                                                              BorrowBooks borrowBooks,
                                                              HttpSession session){
        Reader reader = (Reader) session.getAttribute(bmProperties.getReaderSessionName());
        borrowBooks.setRName(reader.getRName());
        return ResponseEntity.ok(borrowBookService.borrowBookByPage(page,size, borrowBooks,true));
    }

}
