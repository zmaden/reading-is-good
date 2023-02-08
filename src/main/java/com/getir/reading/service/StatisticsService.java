package com.getir.reading.service;

import com.getir.reading.exception.ExceptionFactory;
import com.getir.reading.repository.OrderLineRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public StatisticsService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<Map<String, Object>> getMonthlyReport(String email) {
        logger.info("getMonthlyReport is called. email:{}", email);
        if (StringUtils.isEmpty(email)) {
            ExceptionFactory.throwBadRequestException("Email should not be blank.");
        }
        return orderLineRepository.getMonthlyReport(email);
    }
}
