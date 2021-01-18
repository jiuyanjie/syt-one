package com.bookManagerment.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SalesStatisticsVo {

    private List<String> bookTypes;
    private List<Integer> sales;

    private SalesBooksByTypeVO salesMaxBooksByType;

    public static SalesStatisticsVo initSalesMaxBooksByTypeVO(){
        SalesStatisticsVo salesStatisticsVo = new SalesStatisticsVo();
        salesStatisticsVo.bookTypes = new ArrayList<String>();
        salesStatisticsVo.sales =  new ArrayList<Integer>();
        return salesStatisticsVo;
    }

}
