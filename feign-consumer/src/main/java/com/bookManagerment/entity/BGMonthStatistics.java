package com.bookManagerment.entity;

import com.bookManagerment.bean.BaseBGStatistics;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class BGMonthStatistics extends BaseBGStatistics {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer bgMId;
    private String month;

}
