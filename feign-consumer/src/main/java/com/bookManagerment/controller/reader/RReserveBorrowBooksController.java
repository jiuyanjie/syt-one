package com.bookManagerment.controller.reader;

import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.entity.ReserveBorrowBooks;
import com.bookManagerment.service.ReserveBorrowBooksService;
import com.bookManagerment.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RequestMapping("reader/reserveBorrow")
@RestController
public class RReserveBorrowBooksController {

    @Autowired
    private ReserveBorrowBooksService reserveBookService;

    @Autowired
    private BMSystemProperties bmProperties;

    //分页查询 并可以带查询参数
    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ReserveBorrowBooks>> borrowBookByPage(@PathVariable("page") Integer page,
                                                                     @PathVariable("size") Integer size,
                                                                     ReserveBorrowBooks reserveBorrowBooks,
                                                                     HttpServletRequest request){
        //获取当前用户
        Reader reader = (Reader)request.getSession().getAttribute(bmProperties.getReaderSessionName());
        reserveBorrowBooks.setRId(reader.getRId());  //根据当前用户查找
        return ResponseEntity.ok(reserveBookService.reserveBookByPage(page,size, reserveBorrowBooks));
    }

    //读者修改 借阅天数
    @PutMapping("/{rbbId}/{remainingDays}")
    public ResponseEntity<Void> modifyRemainingDays(@PathVariable("rbbId") Integer rbbId,
                                                    @PathVariable("remainingDays") Integer remainingDays){
        reserveBookService.modifyRemainingDays(rbbId,remainingDays);
        return ResponseEntity.ok().build();
    }

    //读者取消预定 多个
    @DeleteMapping
    public ResponseEntity<Void> deleteReserves(@RequestBody List<Integer> rbbIds){
        reserveBookService.deleteReserve(rbbIds);
        return ResponseEntity.ok().build();
    }

    //读者删除 预定
    @DeleteMapping("/{rbbId}")
    public ResponseEntity<Void> deleteReserve(@PathVariable("rbbId") Integer rbbId){
        reserveBookService.deleteReserve(Arrays.asList(rbbId));
        return ResponseEntity.ok().build();
    }

    //读者 预定图书
    @PostMapping("/{bId}/{RentDay}")
    public ResponseEntity<Integer> reserveBooks(@PathVariable("bId") Integer bId,
                                             @PathVariable("RentDay") Integer rentDay,
                                             HttpSession session){
        return ResponseEntity.ok().body(reserveBookService.reserveBook(bId, rentDay, session));
    }



}
