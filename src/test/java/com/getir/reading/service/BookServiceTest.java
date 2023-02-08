package com.getir.reading.service;

import com.getir.reading.model.Book;
import com.getir.reading.model.Stock;
import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.SaveBookRequest;
import com.getir.reading.model.response.AddBookToStockResponse;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.repository.BookRepository;
import com.getir.reading.repository.StockRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private BookService bookService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SaveBookRequest createSaveBookRequest() {
        SaveBookRequest request = new SaveBookRequest();
        request.setCode("code");
        request.setPrice(10.0);
        return request;
    }

    private SaveBookResponse createSaveBookResponse() {
        SaveBookResponse response = new SaveBookResponse();
        response.setCode("code");
        response.setPrice(10.0);
        return response;
    }

    private AddBookToStockRequest createAddBookToStockRequest() {
        AddBookToStockRequest request = new AddBookToStockRequest();
        request.setCode("code");
        request.setQuantity(10);
        return request;
    }

    private AddBookToStockResponse createAddBookToStockResponse(String code, Integer quantity) {
        AddBookToStockResponse request = new AddBookToStockResponse();
        request.setCode(code);
        request.setQuantity(quantity);
        return request;
    }

    @Test
    public void saveBook_shouldBeError_CodeIsNull() {
        SaveBookRequest request = createSaveBookRequest();
        request.setCode(null);

        expectedException.expect(Exception.class);
        bookService.saveBook(request);
    }

    @Test
    public void saveBook_shouldBeError_PriceIsNull() {
        SaveBookRequest request = createSaveBookRequest();
        request.setPrice(null);

        expectedException.expect(Exception.class);
        bookService.saveBook(request);
    }

    @Test
    public void saveBook_shouldBeError_ConflictBook() {
        SaveBookRequest request = createSaveBookRequest();

        doReturn(new Book()).when(bookRepository).findByCode(anyString());
        expectedException.expect(Exception.class);
        bookService.saveBook(request);
    }

    @Test
    public void saveBook_shouldBeSuccess() {
        SaveBookRequest request = createSaveBookRequest();
        SaveBookResponse response = createSaveBookResponse();

        doReturn(null).when(bookRepository).findByCode(anyString());
        doReturn(new Book()).when(bookRepository).save(any());
        SaveBookResponse result = bookService.saveBook(request);
        Assert.assertEquals(response.getCode(), result.getCode());
        Assert.assertEquals(response.getPrice(), result.getPrice());
    }

    @Test
    public void addBookToStock_shouldBeError_CodeIsNull() {
        AddBookToStockRequest request = createAddBookToStockRequest();
        request.setCode(null);

        expectedException.expect(Exception.class);
        bookService.addBookToStock(request);
    }

    @Test
    public void addBookToStock_shouldBeError_QuantityIsNull() {
        AddBookToStockRequest request = createAddBookToStockRequest();
        request.setQuantity(null);

        expectedException.expect(Exception.class);
        bookService.addBookToStock(request);
    }

    @Test
    public void addBookToStock_shouldBeError_NoSavedBook() {
        AddBookToStockRequest request = createAddBookToStockRequest();

        doReturn(null).when(bookRepository).findByCode(anyString());
        expectedException.expect(Exception.class);
        bookService.addBookToStock(request);
    }

    @Test
    public void addBookToStock_shouldBeSuccess_NoInStock() {
        AddBookToStockRequest request = createAddBookToStockRequest();
        AddBookToStockResponse response = createAddBookToStockResponse(request.getCode(), request.getQuantity());

        doReturn(new Book()).when(bookRepository).findByCode(anyString());
        doReturn(null).when(stockRepository).findByCode(anyString());
        doReturn(new Stock()).when(stockRepository).save(any());
        AddBookToStockResponse result = bookService.addBookToStock(request);
        Assert.assertEquals(response.getCode(), result.getCode());
        Assert.assertEquals(response.getQuantity(), result.getQuantity());
    }

    @Test
    public void addBookToStock_shouldBeSuccess_InStock() {
        int inStock = 15;
        AddBookToStockRequest request = createAddBookToStockRequest();
        int resultInStock = inStock + request.getQuantity();
        AddBookToStockResponse response = createAddBookToStockResponse(request.getCode(), resultInStock);
        Stock stock = new Stock(request.getCode(), inStock);
        Stock resultStock = new Stock(request.getCode(), resultInStock);

        doReturn(new Book()).when(bookRepository).findByCode(anyString());
        doReturn(stock).when(stockRepository).findByCode(anyString());
        doReturn(resultStock).when(stockRepository).save(any());
        AddBookToStockResponse result = bookService.addBookToStock(request);
        Assert.assertEquals(response.getCode(), result.getCode());
        Assert.assertEquals(resultInStock, result.getQuantity());
    }

    @Test
    public void updateStock_shouldBeError_CodeIsNull() {
        expectedException.expect(Exception.class);
        bookService.updateStock(null, 1);
    }

    @Test
    public void updateStock_shouldBeError_QuantityIsNull() {
        expectedException.expect(Exception.class);
        bookService.updateStock("code", -5);
    }

    @Test
    public void updateStock_shouldBeError_NoSavedBook() {
        doReturn(null).when(stockRepository).findByCode(anyString());
        expectedException.expect(Exception.class);
        bookService.updateStock("code", 5);
    }

    @Test
    public void updateStock_shouldBeError_NoEnoughBook() {
        String code = "code";
        int quantity = 5;
        Stock stock = new Stock(code, quantity);
        doReturn(stock).when(stockRepository).findByCode(anyString());
        expectedException.expect(Exception.class);
        bookService.updateStock(code, quantity + 10);
    }

    @Test
    public void updateStock_shouldSuccess() {
        String code = "code";
        int quantity = 5;
        Stock stock = new Stock(code, quantity);
        doReturn(stock).when(stockRepository).findByCode(anyString());
        bookService.updateStock(code, quantity - 1);
    }

    @Test
    public void getBook_shouldBeError_CodeIsNull() {
        expectedException.expect(Exception.class);
        bookService.getBook(null);
    }

    @Test
    public void getBook_shouldSuccess() {
        String code = "code";
        Book book = new Book();
        book.setCode("code");
        book.setPrice(10.0);
        doReturn(book).when(bookRepository).findByCode(anyString());
        Book result = bookService.getBook(code);
        Assert.assertEquals(book.getCode(), result.getCode());
        Assert.assertEquals(book.getPrice(), result.getPrice());
    }
}