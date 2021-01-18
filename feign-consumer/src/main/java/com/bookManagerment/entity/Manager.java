package com.bookManagerment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Data
public class Manager {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer mId;
    private String account;
    private String name;
    private String gender;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String email;
    private String phone;
    private String mtId;

}
