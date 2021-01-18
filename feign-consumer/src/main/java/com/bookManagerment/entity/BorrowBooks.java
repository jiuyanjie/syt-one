package com.bookManagerment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * 借书记录表
 */
@Data
@NoArgsConstructor
public class BorrowBooks {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer bbId;   //编号
    private Integer rId;
    private Integer bId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date bbTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date dueTime;

    @Transient
    private String number;
    @Transient
    private String bName;
    @Transient
    private String rName;
    @Transient
    private Integer remainingDays;  //剩余天数借书天数

    public BorrowBooks(Integer bId) {
        this.bId = bId;
    }

    public BorrowBooks(ReserveBorrowBooks reserveBorrowBooks){
        Date bbTime = new Date(new java.util.Date().getTime());
        Date dueTime = new Date(bbTime.getTime()+(1000*60*60*24* reserveBorrowBooks.getRemainingDays()));
        this.rName = reserveBorrowBooks.getRName();
        this.rId = reserveBorrowBooks.getRId();
        this.bName = reserveBorrowBooks.getBName();
        this.bId = reserveBorrowBooks.getBId();
        this.bbTime = bbTime;
        this.dueTime = dueTime;
        this.remainingDays = reserveBorrowBooks.getRemainingDays();
    }

}
