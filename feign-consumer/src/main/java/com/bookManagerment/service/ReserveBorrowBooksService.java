package com.bookManagerment.service;

import com.bookManagerment.config.BMSystemProperties;
import com.bookManagerment.entity.Books;
import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.entity.ReserveBorrowBooks;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BookMapper;
import com.bookManagerment.mapper.BorrowBookMapper;
import com.bookManagerment.mapper.ReaderMapper;
import com.bookManagerment.mapper.ReserveBorrowBookMapper;
import com.bookManagerment.utils.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReserveBorrowBooksService {

    @Autowired
    private ReserveBorrowBookMapper reserveBookMapper;

    @Autowired
    private BorrowBookMapper borrowBookMapper;
    
    @Autowired
    private BookMapper bookMapper;
    
    @Autowired
    private BMSystemProperties bmProperties;

    @Autowired
    private  ReaderMapper readerMapper;

    public Page<ReserveBorrowBooks> reserveBookByPage(Integer page, Integer size,
                                                      ReserveBorrowBooks reserveBorrowBook) {
        boolean search = false;
        if(!StringUtils.isEmpty(reserveBorrowBook.getBName()) ||
        !StringUtils.isEmpty(reserveBorrowBook.getNumber()) ||
        !StringUtils.isEmpty(reserveBorrowBook.getRName()) ) {
            search = true;
        }
        PageHelper.startPage(page,size);
        List<ReserveBorrowBooks> reserveBorrowBooks = reserveBookMapper.queryReserveBorrowBooks(reserveBorrowBook);
        if(CollectionUtils.isEmpty(reserveBorrowBooks)){
            if(search){ //未搜索到符合条件的预借书记录
                throw new LyException(ExceptionEnum.SEARCH_RESERVE_BOOK_NOT_FOUND);
            }else{  //记录为空
                throw new LyException(ExceptionEnum.RESERVE_BOOK_NOT_FOUND);
            }
        }
        return new Page<ReserveBorrowBooks>(new PageInfo(reserveBorrowBooks));
    }

    //保存多个 借书记录 (借书)
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void saveReserves(List<Integer> resIds) {
        for (Integer resId : resIds) {
            //根据在 reserveBorrowBooks 表中查询数据
            ReserveBorrowBooks reserveBorrowBooks = reserveBookMapper.selectByPrimaryKey(resId);
            if(reserveBorrowBooks == null){    //预借书记录未找到！
                throw new LyException(ExceptionEnum.RESERVE_NOT_FOUND);
            }
            //账户至少有 多少钱才能预定图书
            Reader reader = readerMapper.selectByPrimaryKey(reserveBorrowBooks.getRId());
            if(reader.getBalance()/100.0 < bmProperties.getBorrowBooksAccountLest()){
                //抱歉，{r_name}用户，账户余额少于{balanceLess}元不能借书，请先充值!
                ExceptionEnum em = ExceptionEnum.APPOINT_ACCOUNT_BALANCE_LESS;
                em.setMsg(em.getMsg().replace("{balanceLess}",bmProperties.getBorrowBooksAccountLest()+"")
                .replace("{r_name}",reader.getRName()));
                throw new LyException(em);
            }
            //根据 bId查询图书信息 获取图书的基础租金，逾期租金单位
            Books book = bookMapper.selectByPrimaryKey(reserveBorrowBooks.getBId());
            //将数据插入 borrow_books表中
            BorrowBooks borrowBook = new BorrowBooks(reserveBorrowBooks);
            borrowBookMapper.insert(borrowBook);
            //将预约借书 该预记录删除
            reserveBookMapper.deleteByPrimaryKey(resId);
            //持久化数据
            bookMapper.updateByPrimaryKey(book);
        }
    }

    //读者 预定借书
    @Transactional
    public Integer reserveBook(Integer bId, Integer rentDay, HttpSession session){
        //读者信息
        Reader reader = (Reader) session.getAttribute(bmProperties.getReaderSessionName());
        reader = readerMapper.selectByPrimaryKey(reader.getRId());
        //根据 bId查询图书信息
        Books books = bookMapper.selectByPrimaryKey(bId);
        if(books == null){  //抱歉，该图书信息已失效，请刷新页面!
            throw new LyException(ExceptionEnum.BOOK_INFO_LOSE);
        }
        //是否是开启状态
        if(books.getStatus() == false){ //抱歉，该图书信息正在维护中，暂时不可借阅该图书，请您刷新页面！
            throw new LyException(ExceptionEnum.BOOK_STATUS_FALSE);
        }
        //在馆数
        books.setInLibraryTotal(books.getTotal()-borrowBookMapper.selectCount(new BorrowBooks(books.getBId())));
        //预定数
        books.setReserveNum(reserveBookMapper.selectCount(new ReserveBorrowBooks(books.getBId())));
        if(books.getInLibraryTotal() <= 0){ //抱歉，该图书已全部借出！
            throw new LyException(ExceptionEnum.SORRY_BOOK_BORROW_ALL);
        }
        if(books.getInLibraryTotal() <= books.getReserveNum()){ //抱歉，图书预定人数已满！  在馆数（总数-借出数） == 预定数（预定表中数量）
            throw new LyException(ExceptionEnum.RESERVE_BOOK_FULL);
        }
        //预定天数不能大于 {{maxBorrowBooksDay}} 最大结束天数
        if(rentDay >= bmProperties.getMaxBorrowBooksDay()){
            //抱歉，最大借书天数为{maxBorrowBooksDay}天！
            ExceptionEnum em = ExceptionEnum.GREATER_THAN_MAX_BORROW_BOOKS_DAY;
            em.setMsg(em.getMsg().replace("{maxBorrowBooksDay}",bmProperties.getMaxBorrowBooksDay()+""));
            throw new LyException(em);
        }
        //账户至少有 多少钱才能预定图书
        if(reader.getBalance()/100.0 < bmProperties.getBorrowBooksAccountLest()){
            //抱歉，账户余额少于{balanceLess}元不能借书，请您点击右上角头像选择充值!
            ExceptionEnum em = ExceptionEnum.ACCOUNT_BALANCE_LESS;
            em.setMsg(em.getMsg().replace("{balanceLess}",bmProperties.getBorrowBooksAccountLest()+""));
            throw new LyException(em);
        }
        //封装 预定借书表
        ReserveBorrowBooks reserveBorrowBooks = new ReserveBorrowBooks();
        reserveBorrowBooks.setRId(reader.getRId());
        reserveBorrowBooks.setBId(books.getBId());
        reserveBorrowBooks.setOrderTime(new Timestamp(new Date().getTime()));
        reserveBorrowBooks.setRemainingDays(rentDay);
        //将 该图书记录 插入 预定借阅表中
        reserveBookMapper.insert(reserveBorrowBooks);
        //返回插入的id
        return reserveBorrowBooks.getRbbId();
    }

    //读者 修改剩余天数
    @Transactional
    public void modifyRemainingDays(Integer rbbId, Integer remainingDays) {
        //预定天数不能大于 {{maxBorrowBooksDay}} 最大结束天数
        if(remainingDays >= bmProperties.getMaxBorrowBooksDay()){
            //抱歉，最大借书天数为{maxBorrowBooksDay}天！
            ExceptionEnum em = ExceptionEnum.GREATER_THAN_MAX_BORROW_BOOKS_DAY;
            em.setMsg(em.getMsg().replace("{maxBorrowBooksDay}",bmProperties.getMaxBorrowBooksDay()+""));
            throw new LyException(em);
        }
        //根据id查询 预约图书表中的数据
        ReserveBorrowBooks reserveBorrowBooks = reserveBookMapper.selectByPrimaryKey(rbbId);
        if(reserveBorrowBooks == null){ //预定记录不存在，您的书籍已经成功领取！
            log.error("预约图书表修改剩余天数，失败");
            throw new LyException(ExceptionEnum.BOOK_GET_DOWN);
        }
        //查询图书是否存在
        Books books = bookMapper.selectByPrimaryKey(reserveBorrowBooks.getBId());
        if(books == null){
            throw new LyException(ExceptionEnum.BOOK_INFO_LOSE);
        }
        //修改图书数据
        if(remainingDays > 0){
            reserveBorrowBooks.setRemainingDays(remainingDays);
            //修改预约表中的数据
          reserveBookMapper.updateByPrimaryKey(reserveBorrowBooks);
        }
    }

    //读者 删除预定
    @Transactional
    public void deleteReserve(List<Integer> rbbIds) {
        for (Integer rbbId : rbbIds) {
            //根据id查询 预约图书表中的数据
            ReserveBorrowBooks reserveBorrowBooks = reserveBookMapper.selectByPrimaryKey(rbbId);
            if(reserveBorrowBooks == null){ //图书预定不存在，您的书籍已经成功领取！
                log.error("删除预定失败！");
                throw new LyException(ExceptionEnum.BOOK_GET_DOWN);
            }
            //查询图书信息
            Books books = bookMapper.selectByPrimaryKey(reserveBorrowBooks.getBId());
            if(books == null){  //判断是否存在
                throw new LyException(ExceptionEnum.BOOK_INFO_LOSE);
            }
            //删除数据
            reserveBookMapper.deleteByPrimaryKey(rbbId);
        }
    }
}
