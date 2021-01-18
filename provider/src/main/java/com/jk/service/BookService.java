package com.jk.service;

import com.jk.dto.AppointExecution;
import com.jk.pojo.Appointment;
import com.jk.pojo.BookBean;
import com.jk.pojo.StudentBean;

import java.util.List;

public interface BookService {
    BookBean getById(long bookId);

    List<BookBean>getList();

    StudentBean validateStu(Long studentId,Long password);

    List<BookBean>getSomeList(String name);

    List<Appointment> getAppointByStu(long studentId);

    AppointExecution appoint(long bookId, long studentId);
}
