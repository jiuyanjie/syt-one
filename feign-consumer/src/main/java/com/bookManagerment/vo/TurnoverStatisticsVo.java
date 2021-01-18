package com.bookManagerment.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TurnoverStatisticsVo {

    public static TurnoverStatisticsVo  initTurnoverStatisticsVo(){
        TurnoverStatisticsVo turnoverStatisticsVo = new TurnoverStatisticsVo();
        turnoverStatisticsVo.dates = new ArrayList<String>();
        turnoverStatisticsVo.turnovers = new ArrayList<Float>();
        return turnoverStatisticsVo;
    }

    private List<String> dates;
    private List<Float> turnovers;

}
