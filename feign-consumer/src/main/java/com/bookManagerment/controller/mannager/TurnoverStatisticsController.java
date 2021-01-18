package com.bookManagerment.controller.mannager;

import com.bookManagerment.service.TurnoverStatisticsService;
import com.bookManagerment.vo.TurnoverStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manager/turnoverStatistics")
public class TurnoverStatisticsController {

    @Autowired
    private TurnoverStatisticsService turnoverStatisticsService;

    //营业额统计
    @PostMapping("/{id}/{start}/{end}")
    public ResponseEntity<TurnoverStatisticsVo> turnoverStatistics(@PathVariable("id") Integer id,
                                                                   @PathVariable("start") String start,
                                                                   @PathVariable("end") String end){
        return ResponseEntity.ok(turnoverStatisticsService.turnoverStatistics(id,start,end));
    }

}
