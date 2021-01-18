package com.bookManagerment.mapper;

import com.bookManagerment.entity.GiveBackBooks;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GiveBackBookMapper extends Mapper<GiveBackBooks> {

    List<GiveBackBooks> queryGiveBackBooks(@Param("gbb") GiveBackBooks giveBackBooks, @Param("isReader") Boolean isReader);


}
