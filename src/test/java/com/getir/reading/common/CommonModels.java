package com.getir.reading.common;

import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.SaveBookRequest;

public class CommonModels {

    public static AddBookToStockRequest createAddBookToStockRequest(String code, int quantity) {
        AddBookToStockRequest request = new AddBookToStockRequest();
        request.setCode(code);
        request.setQuantity(quantity);
        return request;
    }

    public static SaveBookRequest createSaveBookRequest() {
        SaveBookRequest request = new SaveBookRequest();
        request.setCode("BOOK-1");
        request.setPrice(25.0);
        return request;
    }
}
