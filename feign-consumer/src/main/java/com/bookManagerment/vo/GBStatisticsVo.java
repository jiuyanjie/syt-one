package com.bookManagerment.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GBStatisticsVo {

    public static GBStatisticsVo initGBStatisticsVo() {
        GBStatisticsVo statisticsVo = new GBStatisticsVo();
        statisticsVo.days = new ArrayList<String>();
        statisticsVo.borrowNumbers = new ArrayList<Integer>();
        statisticsVo.giveBackNumbers = new ArrayList<Integer>();
        return statisticsVo;
    }

    private List<String> days;
    private List<Integer> borrowNumbers;
    private List<Integer> giveBackNumbers;

}
