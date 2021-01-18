package com.bookManagerment.entity;

import com.bookManagerment.bean.BaseTurnoverStatistics;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class TurnoverYearStatistics  extends BaseTurnoverStatistics {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer tYId;
    private String year;

}
