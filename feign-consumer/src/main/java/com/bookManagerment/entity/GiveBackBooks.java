package com.bookManagerment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * 要还书的记录
 */
@Data
@NoArgsConstructor
public class GiveBackBooks {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer gbbId;
    private Integer rId;
    private Integer bId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date bbTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date dueTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date realityTime; //实际还书时间
    @JsonIgnore
    private boolean isOverdue;    //是否逾期
    @JsonIgnore
    private Integer rentalMoney;

    @Transient
    private String number;
    @Transient
    private String rName;
    @Transient
    private String bName;
    @Transient
    private String isOverDueStr;
    @Transient
    private String rentalMoneyStr;

    public String getRentalMoneyStr() {
        if(StringUtils.isBlank(rentalMoneyStr)){
            this.rentalMoneyStr = rentalMoney/100.0+"元";
        }
        return rentalMoneyStr;
    }

    public String getIsOverDueStr() {
        if(StringUtils.isBlank(isOverDueStr)){
            this.isOverDueStr = this.isOverdue?"是":"否";
        }
        return isOverDueStr;
    }

    public GiveBackBooks(BorrowBooks borrowBooks){
        this.rId = borrowBooks.getRId();
        this.bId = borrowBooks.getBId();
        this.bbTime = borrowBooks.getBbTime();
        this.dueTime = borrowBooks.getDueTime();
        this.realityTime = new Date(new java.util.Date().getTime());
    }

}
