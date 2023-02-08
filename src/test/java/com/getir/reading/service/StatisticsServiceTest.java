package com.getir.reading.service;

import com.getir.reading.repository.OrderLineRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private OrderLineRepository orderLineRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getMonthlyReport_shouldSuccess() {
        doReturn(new ArrayList<>()).when(orderLineRepository).getMonthlyReport(anyString());
        List<Map<String, Object>> result = statisticsService.getMonthlyReport("email@email.com");
        Assert.assertNotNull(result);
    }
}