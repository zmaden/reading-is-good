package com.getir.reading.service;

import com.getir.reading.exception.ExceptionFactory;
import com.getir.reading.model.Book;
import com.getir.reading.model.Stock;
import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.SaveBookRequest;
import com.getir.reading.model.response.AddBookToStockResponse;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.repository.BookRepository;
import com.getir.reading.repository.StockRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final StockRepository stockRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       StockRepository stockRepository) {
        this.bookRepository = bookRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public SaveBookResponse saveBook(SaveBookRequest saveBook) {
        logger.info("saveBook is called. code: {}", saveBook.getCode());
        String code = saveBook.getCode();
        if (StringUtils.isEmpty(code)) {
            ExceptionFactory.throwBadRequestException("Code should not be blank.");
        }

        Double price = saveBook.getPrice();
        if (price == null || price <= 0) {
            ExceptionFactory.throwBadRequestException("Not valid value for price.");
        }

        Book existBook = bookRepository.findByCode(code);
        if (existBook != null) {
            ExceptionFactory.throwConflictException(String.format("There is a book for this code : %s.", code));
        }

        Book book = new Book();
        book.setCode(code);
        book.setPrice(price);
        bookRepository.save(book);
        return new SaveBookResponse(code, price);
    }

    @Transactional
    public AddBookToStockResponse addBookToStock(AddBookToStockRequest addBook) {
        logger.info("addBookToStock is called. code: {}", addBook.getCode());
        String code = addBook.getCode();
        if (StringUtils.isEmpty(code)) {
            ExceptionFactory.throwBadRequestException("Code should not be blank.");
        }

        Integer quantity = addBook.getQuantity();
        if (quantity == null || quantity < 1) {
            ExceptionFactory.throwBadRequestException("Please enter valid amount.");
        }

        Book book = bookRepository.findByCode(code);
        if (book == null) {
            ExceptionFactory.throwNotFoundException(String.format("There is no book for %s.", code));
        }

        Stock stock = stockRepository.findByCode(code);
        if (stock == null) {
            stockRepository.save(new Stock(code, quantity));
            return new AddBookToStockResponse(code, quantity);
        }

        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);

        return new AddBookToStockResponse(code, stock.getQuantity());
    }

    @Transactional
    public void updateStock(String code, int quantity) {
        logger.info("updateStock is called. code: {}", code);
        if (StringUtils.isEmpty(code)) {
            ExceptionFactory.throwBadRequestException("Code should not be blank.");
        }

        if (quantity < 1) {
            ExceptionFactory.throwBadRequestException("Please enter valid amount.");
        }

        Stock stock = stockRepository.findByCode(code);
        if (stock == null || stock.getQuantity() == 0) {
            ExceptionFactory.throwBadRequestException(String.format("There is no book in store for %s.", code));
        }
        Integer stockQuantity = stock.getQuantity();
        if (stockQuantity - quantity < 0) {
            ExceptionFactory.throwBadRequestException(String.format("There is no enough book for %s in store.", code));
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }

    public Book getBook(String code) {
        logger.info("getBook is called. code: {}", code);
        if (StringUtils.isEmpty(code)) {
            ExceptionFactory.throwBadRequestException("Code should not be blank.");
        }
        return bookRepository.findByCode(code);
    }
}
