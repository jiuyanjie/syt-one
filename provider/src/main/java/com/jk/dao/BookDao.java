package com.jk.dao;

import com.jk.pojo.BookBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BookDao {
    BookBean queryByid(Long id);

    List<BookBean>querySome(String name);

    List<BookBean>queryAll(@Param("offset")int offset, @Param("limit") int limit);

    int reduceNumber(long bookid);


}
