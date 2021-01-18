package com.bookManagerment.mapper;

import com.bookManagerment.entity.Books;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BookMapper extends Mapper<Books> {

    List<Books> queryBooks(@Param("book") Books book,@Param("status") Boolean status);

}
