package com.bookManagerment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 图书分类表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookType {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer btId; //编号
    private String tName;    //分类名

    public BookType(String tName) {
        this.tName = tName;
    }

    public BookType(Integer btId) {
        this.btId = btId;
    }
}
