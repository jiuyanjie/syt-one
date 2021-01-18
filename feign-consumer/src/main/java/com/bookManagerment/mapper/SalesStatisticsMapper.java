package com.bookManagerment.mapper;

import com.bookManagerment.entity.SalesBooksByType;
import com.bookManagerment.entity.SalesStatistics;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SalesStatisticsMapper {

    @Select("select t_name typeName,count(total) as sales from give_back_books join books using(b_id) right join book_type using(bt_id) GROUP BY bt_id order by sales desc")
    public List<SalesStatistics> queryBookTypeSales();

    @Select("select number id,count(gbb_id) sales from give_back_books right join (select b_id,bt_id,number from books join book_type using(bt_id) where t_name = #{tName} ) t using(b_id)  GROUP BY id order by sales desc")
    public List<SalesBooksByType> querySalesBooksByType(@Param("tName") String tName);

}
