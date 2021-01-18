package com.jk.dao;

import com.jk.pojo.StudentBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentDao {

    StudentBean quaryStudent(@Param("studentid")long studentid,@Param("password")long password);

}
