package com.getir.reading.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ListCustomerOrderResponse {
    private List<OrderResponse> orderResponses;
    private Integer page;
    private Integer pageSize;

    public ListCustomerOrderResponse(List<OrderResponse> orderResponses, Integer page, Integer pageSize) {
        this.orderResponses = orderResponses;
        this.page = page;
        this.pageSize = pageSize;
    }
}
