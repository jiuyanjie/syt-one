package com.bookManagerment.entity;

import com.bookManagerment.bean.BaseBGStatistics;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * 借书and归还日报表
 */
@Data
public class BGDayStatistics extends BaseBGStatistics {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer bgDId;
    private Date day;

}
