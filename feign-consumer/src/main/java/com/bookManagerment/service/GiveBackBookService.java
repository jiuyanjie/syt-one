package com.bookManagerment.service;

import com.bookManagerment.entity.Books;
import com.bookManagerment.entity.BorrowBooks;
import com.bookManagerment.entity.GiveBackBooks;
import com.bookManagerment.entity.Reader;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.*;
import com.bookManagerment.utils.DateUtils;
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

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GiveBackBookService {

    @Autowired
    private GiveBackBookMapper giveBackBookMapper;

    @Autowired
    private BorrowBookMapper borrowBookMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReaderMapper readerMapper;

    //根据 bbId 还书
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public GiveBackBooks giveBackBookById(Integer bbId) {
        //根据 bbId 查询在 借阅表 是否存在
        BorrowBooks borrowBooksDB = borrowBookMapper.selectByPrimaryKey(bbId);
        if(borrowBooksDB == null){   //该图书已被其他管理员归还
            throw new LyException(ExceptionEnum.GIVE_BACK_BOOK_RESERVE_GIVE_BACK_NOT_FOUND);
        }
        //封装 还书信息
        GiveBackBooks giveBackBooks = new GiveBackBooks(borrowBooksDB);
        //产生的租金
        int rentalMoney = 0;
        //查询图书信息
        Books books = bookMapper.selectByPrimaryKey(borrowBooksDB.getBId());
        //实际借书天数（现在的时间-租借的时间）
        int readyDay =  DateUtils.differenceDay(new Date(),borrowBooksDB.getBbTime());
        //最大借书天数 = 应该还书的时间-租借的时间
        int maxDay = DateUtils.differenceDay(borrowBooksDB.getDueTime(),borrowBooksDB.getBbTime());
        //逾期天数 = 实际借书天数 - 最大借书天数
        int overDay = readyDay - maxDay;
        //是否逾期  逾期天数 > 0 逾期
        if(overDay > 0){    //逾期
            //基础租金 = 最大租借天数 * 基础租金单位
            int baseMoney = maxDay * books.getRentalUnit();
            //逾期租金 = 逾期天数 * 逾期租金单位
            int overMoney = overDay * books.getOverDueUnit();
            //逾期计算租金 基础租金 + 逾期租金
            rentalMoney = baseMoney + overMoney;
            //设置逾期
            giveBackBooks.setOverdue(true);
        }else{  //未逾期
            //借书天数不足一天
            if(readyDay <= 0){
                readyDay = 1;   //不足一天按一天计算
            }
            //租金 = 实际借书天数 * 基础租金单位
            rentalMoney = readyDay * books.getRentalUnit();
        }
        //设置租金
        giveBackBooks.setRentalMoney(rentalMoney);
        //查询用户
        Reader reader = readerMapper.selectByPrimaryKey(borrowBooksDB.getRId());
        //用户账户金额扣除租金
        reader.setBalance(reader.getBalance() - rentalMoney);
        //持久化用户数据
        readerMapper.updateByPrimaryKey(reader);
        //将 BorrowBooks 中的借书记录删除
        borrowBookMapper.deleteByPrimaryKey(borrowBooksDB.getBbId());
        //持久化 还书数据
        giveBackBookMapper.insert(giveBackBooks);
        //封装返回前端的数据
        giveBackBooks.setBName(books.getBName());
        giveBackBooks.setRName(reader.getRName());
        return giveBackBooks;
    }

    //获取 归还信息
    public Page<GiveBackBooks> giveBackBook(Integer page, Integer size, GiveBackBooks giveBackBooks,Boolean isReader) {
        boolean search = false;
        PageHelper.startPage(page,size);
        if(!StringUtils.isEmpty(giveBackBooks.getBName()) ||
                !StringUtils.isEmpty(giveBackBooks.getNumber()) ||
                !StringUtils.isEmpty(giveBackBooks.getRName()) ){
            search = true;
        }
        List<GiveBackBooks> giveBackBooksList = giveBackBookMapper.queryGiveBackBooks(giveBackBooks,isReader);
        if(CollectionUtils.isEmpty(giveBackBooksList)){
            if(search){ //未搜索到符合条件的预借书记录
                throw new LyException(ExceptionEnum.RESERVE_GIVE_BACK_NOT_FOUND);
            }else{  //记录为空
                throw new LyException(ExceptionEnum.SEARCH_RESERVE_GIVE_BACK_NOT_FOUND);
            }
        }
        return new Page<GiveBackBooks>(new PageInfo(giveBackBooksList));
    }
}
