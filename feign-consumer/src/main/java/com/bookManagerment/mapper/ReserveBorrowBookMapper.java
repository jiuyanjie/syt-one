package com.bookManagerment.mapper;

import com.bookManagerment.entity.ReserveBorrowBooks;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReserveBorrowBookMapper extends Mapper<ReserveBorrowBooks> {

    List<ReserveBorrowBooks> queryReserveBorrowBooks(ReserveBorrowBooks reserveBorrowBooks);

}
