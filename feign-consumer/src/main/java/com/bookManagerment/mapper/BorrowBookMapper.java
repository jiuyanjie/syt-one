package com.bookManagerment.mapper;

import com.bookManagerment.entity.BorrowBooks;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BorrowBookMapper extends Mapper<BorrowBooks> {

    List<BorrowBooks> queryBorrowBooks(@Param("bb") BorrowBooks books,@Param("isReader") boolean isReader);


    @Select("select  borrow_books.*,reader.r_name ,books.b_name,DATEDIFF(due_time,now()) 'remainingDays' from borrow_books join reader using(r_id) join books using(b_id) where bb_id = #{bbId}")
    BorrowBooks queryBorrowBookByBbId(@Param("bbId") Integer bbId);
}
