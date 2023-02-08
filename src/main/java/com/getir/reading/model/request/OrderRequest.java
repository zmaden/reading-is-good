package com.getir.reading.model.request;

import lombok.Data;

import java.util.List;
@Data
public class OrderRequest {

    private List<OrderLineRequest> orderLines;
}
