package com.bookManagerment.entity;

import com.bookManagerment.bean.BaseBGStatistics;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class BGYearStatistics extends BaseBGStatistics {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer bgYId;
    private String year;

}
