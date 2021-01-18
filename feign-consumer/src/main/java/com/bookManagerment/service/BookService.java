package com.bookManagerment.service;

import com.bookManagerment.entity.Books;
import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.entity.ReserveBorrowBooks;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BookMapper;
import com.bookManagerment.mapper.BorrowBookMapper;
import com.bookManagerment.mapper.ReserveBorrowBookMapper;
import com.bookManagerment.utils.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowBookMapper borrowBookMapper;

    @Autowired
    private ReserveBorrowBookMapper reserveBorrowBookMapper;

    /**
     *分页获取 全部图书信息
     * @param page
     * @param size
     * @param book
     * @return
     */
    public Page<Books> getBookList(Integer page, Integer size, Books book){
        return getBookList(page,size,book,null);
    }

    /**
     *分页获取 开启状态的图书信息
     * @param page
     * @param size
     * @param book
     * @return
     */
    public Page<Books> getBookListStatusTrue(Integer page, Integer size, Books book){
        return getBookList(page,size,book,true);
    }

    /**
     * 分页获取图书信息，并可以搜索
     * @param page
     * @param size
     * @param book
     * @param status null 查询全部，true查询开启状态的图书，false查看关闭状态的图书
     * @return
     */
    private Page<Books> getBookList(Integer page, Integer size, Books book,Boolean status){
        boolean search = false;
        PageHelper.startPage(page,size);
         List<Books> books = bookMapper.queryBooks(book,status);
         //是否为搜索
         if(book.getBtId()!=null || !StringUtils.isEmpty(book.getBName()) ||
         !StringUtils.isEmpty(book.getPublisher()) || !StringUtils.isEmpty(book.getNumber()) ||
         status != null){
             search = true;
         }
         //没有搜索到数据库
        if(CollectionUtils.isEmpty(books)){
            if(search){ //未搜索到符合条件的图书信息
                throw new LyException(ExceptionEnum.SEARCH_BOOK_NOT_FOUND);
            }else{
                throw new LyException(ExceptionEnum.BOOK_NOT_FOUND);
            }
        }
        //计算图书数据
        for (int i=0;i<books.size();i++) {
            Books book1 = books.get(i);
            Integer inLibraryTotal = book1.getTotal()-borrowBookMapper.selectCount(new BorrowBooks(book1.getBId()));
            if(status !=null && status == true && inLibraryTotal == 0){
                books.remove(i);
                i--;
                continue;   //在馆数为0 并且 是读者查询图书信息，直接进行下一次循环
            }
            //图书在馆数 = 总数-借出总数
            book1.setInLibraryTotal(inLibraryTotal);
            //图书预约人数
            book1.setReserveNum(reserveBorrowBookMapper.selectCount(new ReserveBorrowBooks(book1.getBId())));
        }
        return new Page<Books>(new PageInfo(books));
    }


    //判断图书编号是否已经存在
    public boolean existBook(String number){
        Books bookQuery = new Books(number);
        List<Books> select = bookMapper.select(bookQuery);
        return !CollectionUtils.isEmpty(select);
    }

    //添加 图书
    public void addBook(Books books) {
        if(existBook(books.getNumber())){   //图书已存在
            throw new LyException(ExceptionEnum.BOOK_EXIST);
        }
        books.buildAddBook(books);
        int insert = bookMapper.insert(books);
        if(insert<0){ //添加 失败
            throw new LyException(ExceptionEnum.ADD_BOOK_FAIL);
        }
    }

    //删除 图书 多个 根据图书id
    @Transactional
    public void deleteBookBybIds(List<Integer> ids) {
        for (Integer id : ids) {
            //查询图书是否存在
            Books book = bookMapper.selectByPrimaryKey(id);
            if(book == null){  //该图书不在系统中 该图书已被其他管理人员删除！
                throw new LyException(ExceptionEnum.BOOK_DELETED);
            }
            //查询该图书是否已经被租借
            if( !CollectionUtils.isEmpty(borrowBookMapper.select(new BorrowBooks(id))) ){   //已经被租借，不能删除该图书
                if(ids.size()==1){  //该图书正在被租借，删除失败！
                    throw new LyException(ExceptionEnum.THIS_BOOK_RENTED);
                }else{  //删除的图书中有正在租借的图书，删除失败！
                    throw new LyException(ExceptionEnum.BOOKS_INCLUDE_RENTED);
                }
            }
            //删除图书
            int i = bookMapper.deleteByPrimaryKey(book.getBId());
            if(i<0){ //删除 失败
                throw new LyException(ExceptionEnum.DELETE_BOOK_FAIL);
            }
        }
    }

    //修改 图书
    @Transactional
    public void modifyBook(Books books) {
        Books booksDB = bookMapper.selectByPrimaryKey(books.getBId());
        if(booksDB == null){    // 抱歉图书不存在，图书已被其他管理人员删除
            throw new LyException(ExceptionEnum.BOOK_DELETED);
        }
        //修改图书的编号
        if( !booksDB.getNumber().equals(books.getNumber()) ){
            if(existBook(books.getNumber())){   //图书已存在
                throw new LyException(ExceptionEnum.BOOK_EXIST);
            }
        }
        books.setStatus(false);
        int i = bookMapper.updateByPrimaryKey(books);
        if(i<0){ //修改 失败
            throw new LyException(ExceptionEnum.UPDATE_BOOK_FAIL);
        }
    }

    //根据图书bId修改图书的状态
    @Transactional
    public void modifyBookStatus(Integer bId, Boolean status) {
        //查找图书
        Books books = bookMapper.selectByPrimaryKey(bId);
        if(books == null){
            throw new LyException(ExceptionEnum.BOOK_DELETED);
        }
        //设置状态
        books.setStatus(status);
        //修改图书
        bookMapper.updateByPrimaryKey(books);
    }

    //修改图书总数量
    @Transactional
    public void modifyBookTotal(Integer bId, Integer total) {
        //查询图书是否存在
        Books books = bookMapper.selectByPrimaryKey(bId);
        if(books == null){  //图书不存在
            throw new LyException(ExceptionEnum.BORROW_BOOK_BY_ID_NOT_FOUND);
        }
        //修改在馆数
        Integer inLibrary = books.getTotal() - reserveBorrowBookMapper.selectCount(new ReserveBorrowBooks(books.getBId()));
        books.setInLibraryTotal(inLibrary + (total - books.getTotal()));
        //修改图书总数
        books.setTotal(total);
        //持久化图书记录
        bookMapper.updateByPrimaryKey(books);
    }
}
