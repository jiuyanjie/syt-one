package com.bookManagerment.service;

import com.bookManagerment.bean.BaseTurnoverStatistics;
import com.bookManagerment.entity.TurnoverDayStatistics;
import com.bookManagerment.entity.TurnoverMonthStatistics;
import com.bookManagerment.entity.TurnoverYearStatistics;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.TurnoverDayStatisticsMapper;
import com.bookManagerment.mapper.TurnoverMonthStatisticsMapper;
import com.bookManagerment.mapper.TurnoverYearStatisticsMapper;
import com.bookManagerment.utils.DateUtils;
import com.bookManagerment.vo.TurnoverStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TurnoverStatisticsService {

    @Autowired
    private TurnoverDayStatisticsMapper dayStatisticsMapper;

    @Autowired
    private TurnoverMonthStatisticsMapper monthStatisticsMapper;

    @Autowired
    private TurnoverYearStatisticsMapper yearStatisticsMapper;

    public TurnoverStatisticsVo turnoverStatistics(Integer id, String start, String end) {
        Mapper mapper = null;
        Example example = null;
        String date = null;
        if(id == 3){
            mapper = dayStatisticsMapper;
            date = "day";
            example = new Example(TurnoverDayStatistics.class);
        }else if(id == 2){
            mapper = monthStatisticsMapper;
            date = "month";
            example = new Example(TurnoverMonthStatistics.class);
        } else if (id == 1) {
            mapper = yearStatisticsMapper;
            date = "year";
            example = new Example(TurnoverYearStatistics.class);
        }else{
            //异常
            System.out.println("抛出异常");
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo(date,start);
        criteria.andLessThanOrEqualTo(date,end);
        List<BaseTurnoverStatistics> list = mapper.selectByExample(example);

        //未查询到数据
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.QUERY_BY_RANGE_NOT_FOUND);
        }

        TurnoverStatisticsVo turnoverStatisticsVo = TurnoverStatisticsVo.initTurnoverStatisticsVo();
        List<String> days = turnoverStatisticsVo.getDates();
        List<Float> turnovers = turnoverStatisticsVo.getTurnovers();

        for (BaseTurnoverStatistics o : list) {
            if(id == 3){
                TurnoverDayStatistics bgDayStatistics = (TurnoverDayStatistics) o;
                String s = DateUtils.dateFormat("MM-dd", bgDayStatistics.getDay());
                days.add(s);
            }else if(id == 2){
                TurnoverMonthStatistics monthStatistics = (TurnoverMonthStatistics) o;
                String month = monthStatistics.getMonth();
                month = month.substring(month.indexOf("-")+1);
                days.add(month);
            } else if (id == 1) {
                TurnoverYearStatistics yearStatistics = (TurnoverYearStatistics) o;
                days.add(yearStatistics.getYear());
            }else{
                //异常
                System.out.println("抛出异常");
            }
            turnovers.add(o.getTurnover()/100.0F);
        }
        return turnoverStatisticsVo;
    }
}
