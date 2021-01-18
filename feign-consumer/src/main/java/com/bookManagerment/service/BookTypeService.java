package com.bookManagerment.service;

import com.bookManagerment.entity.BookType;
import com.bookManagerment.entity.Books;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BookMapper;
import com.bookManagerment.mapper.BookTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Autowired
    private BookMapper bookMapper;

    //获取类别
    public List<BookType> bookTypeListAll(){
        List<BookType> list = bookTypeMapper.selectAll();
        if(CollectionUtils.isEmpty(list)){  //图书类别不存在
            throw new LyException(ExceptionEnum.BOOK_TYPE_NOT_FOUND);
        }
        return list;
    }

    //获取类别
    public List<BookType> bookTypeList(){
        Example example = new Example(BookType.class);
        example.createCriteria().andNotEqualTo("btId",1);
        example.orderBy("btId").asc();
        List<BookType> list = bookTypeMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){  //图书类别不存在
            throw new LyException(ExceptionEnum.BOOK_TYPE_NOT_FOUND);
        }
        return list;
    }

    //添加图书类别
    public void addBookType(String tName) {
        if(existBookName(tName)){  //分类名已存在
            throw new LyException(ExceptionEnum.BOOK_TYPE_EXIST);
        }
        int i = bookTypeMapper.insertSelective(new BookType(tName));
        if(i<0){    //添加失败
            throw new LyException(ExceptionEnum.ADD_BOOK_TYPE_FAIL);
        }
    }

    //删除多个图书分类
    @Transactional
    public void deleteBookTypes(List<Integer> ids) {
        for (Integer id : ids) {
            int count = bookTypeMapper.selectCount(new BookType(id));
            if(count<=0){
                throw new LyException(ExceptionEnum.DELETE_BOOK_TYPE_FAIL);
            }
            int i = bookTypeMapper.deleteByPrimaryKey(id);
            //将该图书分类下的所有图书设置为暂定
            Books books = new Books(1);
            Example example = new Example(Books.class);
            example.createCriteria().andEqualTo("btId",id);
            bookMapper.updateByExampleSelective(books,example);
            if(i<0){ //删除失败
                throw new LyException(ExceptionEnum.DELETE_BOOK_TYPE_FAIL);
            }
        }
    }


    //修改图书分类
    @Transactional
    public void modifyBookType(BookType bookType) {
        if(existBookName(bookType.getTName())){  //分类名已存在
            throw new LyException(ExceptionEnum.BOOK_TYPE_EXIST);
        }
        int countBookType = bookTypeMapper.selectCount(new BookType(bookType.getBtId()));
        if(countBookType<=0){
            throw new LyException(ExceptionEnum.UPDATE_BOOK_TYPE_FAIL);
        }
        //修改图书分类
        int i = bookTypeMapper.updateByPrimaryKey(bookType);
        if(i<0){ //修改失败
            throw new LyException(ExceptionEnum.UPDATE_BOOK_TYPE_FAIL);
        }
    }

    //判断图书名是否存在
    private boolean existBookName(String tName){
        return !CollectionUtils.isEmpty(bookTypeMapper.select(new BookType(tName)));
    }


}
