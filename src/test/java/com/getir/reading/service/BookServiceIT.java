package com.getir.reading.service;

import com.getir.reading.base.BaseIntegrationTest;
import com.getir.reading.common.CommonModels;
import com.getir.reading.model.Book;
import com.getir.reading.model.Stock;
import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.SaveBookRequest;
import com.getir.reading.model.response.AddBookToStockResponse;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.repository.StockRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class BookServiceIT extends BaseIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void saveBook() {
        SaveBookRequest request = CommonModels.createSaveBookRequest();

        SaveBookResponse result = bookService.saveBook(request);
        assertEquals(request.getCode(), result.getCode());
        assertEquals(request.getPrice(), result.getPrice());
    }

    @Test
    public void addBookToStock() {
        SaveBookResponse bookResult = bookService.saveBook(CommonModels.createSaveBookRequest());

        int quantity = 10;
        AddBookToStockRequest request = CommonModels.createAddBookToStockRequest(bookResult.getCode(), quantity);

        AddBookToStockResponse result = bookService.addBookToStock(request);
        assertEquals(request.getCode(), result.getCode());
        assertEquals(quantity, result.getQuantity());
    }

    @Test
    public void updateStock() {
        SaveBookResponse bookResult = bookService.saveBook(CommonModels.createSaveBookRequest());

        int quantity = 100;
        String code = bookResult.getCode();
        AddBookToStockRequest request = CommonModels.createAddBookToStockRequest(code, quantity);
        bookService.addBookToStock(request);

        int quantityAdded = 20;
        bookService.updateStock(code, quantityAdded);

        Stock stock = stockRepository.findByCode(code);
        assertEquals(request.getCode(), stock.getCode());
        assertEquals(quantity - quantityAdded, stock.getQuantity().intValue());
    }

    @Test
    public void getBook() {
        SaveBookResponse book = bookService.saveBook(CommonModels.createSaveBookRequest());

        Book result = bookService.getBook(book.getCode());
        assertEquals(book.getCode(), result.getCode());
        assertEquals(book.getPrice(), result.getPrice());
    }
}
