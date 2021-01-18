package com.bookManagerment.controller.mannager;

import com.bookManagerment.service.SalesStatisticsService;
import com.bookManagerment.vo.SalesBooksByTypeVO;
import com.bookManagerment.vo.SalesStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manager")
public class SalesStatisticsController {

    @Autowired
    private SalesStatisticsService salesStatisticsService;

    //根据分类获取图书销量
    @RequestMapping("/salesByType/{typeName}")
    public ResponseEntity<SalesBooksByTypeVO> salesByType(@PathVariable("typeName") String typeName){
        return ResponseEntity.ok(salesStatisticsService.querySalesBooksByType(typeName));
    }

    //获取按销量降序获取图书分类，以及销量最高的第一个分类下的图书销量
    @RequestMapping("/sales")
    public ResponseEntity<SalesStatisticsVo> sales(){
        return ResponseEntity.ok(salesStatisticsService.queryBookTypeSales());
    }

}
