package com.bookManagerment.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class MailSymbol {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String name;
    private String value;

}
