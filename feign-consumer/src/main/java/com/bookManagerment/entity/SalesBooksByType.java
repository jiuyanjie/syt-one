package com.bookManagerment.entity;

import lombok.Data;

@Data
public class SalesBooksByType {

    private String id; //图书编号
    private Integer sales; // 借出次数

}
