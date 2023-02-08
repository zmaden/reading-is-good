package com.getir.reading.controller;

import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.SaveBookRequest;
import com.getir.reading.model.response.AddBookToStockResponse;
import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/saveBook")
    public ApiResponse<SaveBookResponse> saveBook(@RequestBody SaveBookRequest request) {
        return ApiResponse.response(bookService.saveBook(request));
    }

    @PostMapping("/addBookToStock")
    public ApiResponse<AddBookToStockResponse> addBookToStock(@RequestBody AddBookToStockRequest addBook) {
        return ApiResponse.response(bookService.addBookToStock(addBook));
    }
}
