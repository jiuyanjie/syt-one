package com.bookManagerment.service;

import com.bookManagerment.bean.BaseBGStatistics;
import com.bookManagerment.entity.BGDayStatistics;
import com.bookManagerment.entity.BGMonthStatistics;
import com.bookManagerment.entity.BGYearStatistics;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.BGDayStatisticsMapper;
import com.bookManagerment.mapper.BGMonthStatisticsMapper;
import com.bookManagerment.mapper.BGYearStatisticsMapper;
import com.bookManagerment.utils.DateUtils;
import com.bookManagerment.vo.GBStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BGStatisticsService {

    @Autowired
    private BGDayStatisticsMapper dayStatisticsMapper;

    @Autowired
    private BGMonthStatisticsMapper monthStatisticsMapper;

    @Autowired
    private BGYearStatisticsMapper yearStatisticsMapper;

    //借书/归还统计
    public GBStatisticsVo GBStatistics(Integer id, String start, String end) {
        Mapper mapper = null;
        Example example = null;
        String date = null;
        if(id == 3){
            mapper = dayStatisticsMapper;
            date = "day";
            example = new Example(BGDayStatistics.class);
        }else if(id == 2){
            mapper = monthStatisticsMapper;
            date = "month";
            example = new Example(BGMonthStatistics.class);
        } else if (id == 1) {
            mapper = yearStatisticsMapper;
            date = "year";
            example = new Example(BGYearStatistics.class);
        }else{
            //异常
            System.out.println("抛出异常");
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo(date,start);
        criteria.andLessThanOrEqualTo(date,end);
        List<BaseBGStatistics> list = mapper.selectByExample(example);

        //未查询到数据
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.QUERY_BY_RANGE_NOT_FOUND);
        }

        GBStatisticsVo gbStatisticsVo = GBStatisticsVo.initGBStatisticsVo();
        List<String> days = gbStatisticsVo.getDays();
        List<Integer> borrowNumbers = gbStatisticsVo.getBorrowNumbers();
        List<Integer> giveBackNumbers = gbStatisticsVo.getGiveBackNumbers();

        for (BaseBGStatistics o : list) {
            if(id == 3){
                BGDayStatistics bgDayStatistics = (BGDayStatistics) o;
                String s = DateUtils.dateFormat("MM-dd", bgDayStatistics.getDay());
                days.add(s);
            }else if(id == 2){
                BGMonthStatistics monthStatistics = (BGMonthStatistics) o;
                String month = monthStatistics.getMonth();
                month = month.substring(month.indexOf("-")+1);
                days.add(month);
            } else if (id == 1) {
                BGYearStatistics yearStatistics = (BGYearStatistics) o;
                days.add(yearStatistics.getYear());
            }else{
                //异常
                System.out.println("抛出异常");
            }
            borrowNumbers.add(o.getBbNum());
            giveBackNumbers.add(o.getGbNum());
        }
        return gbStatisticsVo;
    }

}
