package com.bookManagerment.service;

import com.bookManagerment.entity.SalesBooksByType;
import com.bookManagerment.entity.SalesStatistics;
import com.bookManagerment.enums.ExceptionEnum;
import com.bookManagerment.exception.LyException;
import com.bookManagerment.mapper.SalesStatisticsMapper;
import com.bookManagerment.vo.SalesBooksByTypeVO;
import com.bookManagerment.vo.SalesStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SalesStatisticsService {
    
    @Autowired
    private SalesStatisticsMapper statisticsMapper;
    
    public SalesStatisticsVo queryBookTypeSales(){
        SalesStatisticsVo salesStatisticsVo = SalesStatisticsVo.initSalesMaxBooksByTypeVO();
        List<SalesStatistics> salesStatistics = statisticsMapper.queryBookTypeSales();
        //未查询到分类销量信息
        if(CollectionUtils.isEmpty(salesStatistics)){
            throw new LyException(ExceptionEnum.BOOK_TYPE_NOT_FOUND);
        }
        //封装分类销量信息
        List<String> bookTypes = salesStatisticsVo.getBookTypes();
        List<Integer> sales = salesStatisticsVo.getSales();
        for (SalesStatistics salesStatistic : salesStatistics) {
            String typeName = salesStatistic.getTypeName();
            Integer sales1 = salesStatistic.getSales();
            bookTypes.add(typeName);
            sales.add(sales1);
        }
        //查询销量最高的分类下的图书
        String maxBookType = salesStatistics.get(0).getTypeName();
        // 查询图书销量最高的图书根据图书分类
        salesStatisticsVo.setSalesMaxBooksByType(querySalesBooksByType(maxBookType));
        return salesStatisticsVo;
    }

    public SalesBooksByTypeVO querySalesBooksByType(String tName){
        List<SalesBooksByType> salesBooksByTypes = statisticsMapper.querySalesBooksByType(tName);
        //根据分类名字查询图书分类销量统计（从归还表中）
        if(CollectionUtils.isEmpty(salesBooksByTypes)){
            throw new LyException(ExceptionEnum.QUERY_BY_BOOK_TYPE_NOT_FOUND);
        }
        SalesBooksByTypeVO salesBooksByTypeVO = new SalesBooksByTypeVO(tName);
        List<String> ids = salesBooksByTypeVO.getId();
        List<Integer> sales1 = salesBooksByTypeVO.getSales();
        for (SalesBooksByType salesBooksByType : salesBooksByTypes) {
            String id = salesBooksByType.getId();
            Integer sales2 = salesBooksByType.getSales();
            ids.add(id);
            sales1.add(sales2);
        }
        return salesBooksByTypeVO;
    }
    
}
