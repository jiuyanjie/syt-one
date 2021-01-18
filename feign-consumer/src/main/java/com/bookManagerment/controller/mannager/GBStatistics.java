package com.bookManagerment.controller.mannager;

import com.bookManagerment.service.BGStatisticsService;
import com.bookManagerment.vo.GBStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manager/GBStatistics")
public class GBStatistics {

    @Autowired
    private BGStatisticsService bgStatisticsService;

    //借书/归还统计
    @PostMapping("/{id}/{start}/{end}")
    public ResponseEntity<GBStatisticsVo> BStatistics(@PathVariable("id") Integer id,
                                                       @PathVariable("start") String start,
                                                       @PathVariable("end") String end){
        return ResponseEntity.ok(bgStatisticsService.GBStatistics(id,start,end));
    }


}
