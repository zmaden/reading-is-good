package com.getir.reading.controller;

import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/getMonthlyReport")
    public ApiResponse<List<Map<String, Object>>> getMonthlyReport(String email) {
        return ApiResponse.response(statisticsService.getMonthlyReport(email));
    }
}
