package com.bookManagerment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;

/**
 * 预定借书表
 */
@Data
@NoArgsConstructor
public class ReserveBorrowBooks {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer rbbId;
    private Integer rId;
    private Integer bId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp orderTime;
    private Integer remainingDays;

    @Transient
    private String number;  //图书编号
    @Transient
    private String bName;
    @Transient
    private String rName;

    public ReserveBorrowBooks(Integer bId) {
        this.bId = bId;
    }


}
