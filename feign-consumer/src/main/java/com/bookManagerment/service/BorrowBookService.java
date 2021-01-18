package com.bookManagerment.service;

import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BorrowBookMapper;
import com.bookManagerment.utils.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BorrowBookService {

    @Autowired
    private BorrowBookMapper borrowBookMapper;

    public Page<BorrowBooks> borrowBookByPage(Integer page, Integer size, BorrowBooks borrowBook,boolean isReader) {
        boolean search = false;
        if(!StringUtils.isEmpty(borrowBook.getRName()) && isReader ||
        !StringUtils.isEmpty(borrowBook.getBName()) ||
        borrowBook.getRemainingDays() != null){
            search = true;
        }
        PageHelper.startPage(page,size);
        List<BorrowBooks> borrowBooks  = borrowBookMapper.queryBorrowBooks(borrowBook,isReader);
        if(CollectionUtils.isEmpty(borrowBooks)){
            if(search){ //未搜索到符合条件的借书记录
                throw new LyException(ExceptionEnum.SEARCH_BORROW_BOOK_NOT_FOUND);
            }else{
                throw new LyException(ExceptionEnum.BORROW_BOOK_NOT_FOUND);
            }
        }
        return new Page<BorrowBooks>(new PageInfo(borrowBooks));
    }

}
