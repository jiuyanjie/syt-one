package com.jk.dao;

import com.jk.pojo.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AppointmentDao {
    int insertAppointment(@Param("bookid")long bookid,@Param("studentid")long stydentid);

    List<Appointment>quaryAndReturn(Long studentid);

}
