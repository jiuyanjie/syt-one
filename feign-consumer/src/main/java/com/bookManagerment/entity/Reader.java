package com.bookManagerment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * 读者
 */
@Data
@NoArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer rId;
    private String account;
    private String password;
    private String rName;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String email;
    private String phone;
    private Integer balance;    //余额
    @JsonIgnore
    @Transient
    private String lastLoginTimeStr;
    @Transient
    private String verifyCode;

    public Reader(String account) {
        this.account = account;
    }

    //用户注册时插入需要用到的数据
    public void builderReader() {
        this.balance = 0;
    }
}
