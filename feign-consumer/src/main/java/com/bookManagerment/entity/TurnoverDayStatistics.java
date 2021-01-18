package com.bookManagerment.entity;

import com.bookManagerment.bean.BaseTurnoverStatistics;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class TurnoverDayStatistics extends BaseTurnoverStatistics {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer tDId;
    private Date day;

}
