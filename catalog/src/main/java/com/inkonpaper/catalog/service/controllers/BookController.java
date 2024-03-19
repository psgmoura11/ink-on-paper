package com.inkonpaper.catalog.service.controllers;

import com.inkonpaper.catalog.service.controllers.dtos.BookOutputDto;
import com.inkonpaper.catalog.service.controllers.dtos.BookRequestInputDto;
import com.inkonpaper.catalog.service.mappers.BookMapper;
import com.inkonpaper.catalog.service.services.BookService;
import com.inkonpaper.catalog.service.services.BookServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookServiceImpl bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<List<BookOutputDto>> getBooks() {

    var books = bookService.getBooks();
    var response = BookMapper.INSTANCE.booksToBookDtos(books);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{name}")
  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackGetBookByName")
  @Retry(name = "getBookByName", fallbackMethod = "fallbackGetBookByName")
  public ResponseEntity<BookOutputDto> getBookByName(@PathVariable String name) {

    var book = bookService.getBookByName(name);
    if (book.isPresent()) {
      var response = BookMapper.INSTANCE.bookToBookDto(book.get());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/isbn/{isbn}")
  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackGetBookByIsbn")
  @Retry(name = "getBookByIsbn", fallbackMethod = "fallbackGetBookByIsbn")
  public ResponseEntity<BookOutputDto> getBookByIsbn(@PathVariable String isbn) {

    var book = bookService.getBookByIsbn(isbn);
    if (book.isPresent()) {
      var response = BookMapper.INSTANCE.bookToBookDto(book.get());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Void> createBook(@RequestBody BookRequestInputDto bookRequest)
      throws Exception {

    bookService.createBook(bookRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

    if (bookService.deleteBook(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateBook(@PathVariable Long id,
      @RequestBody BookRequestInputDto bookRequest) {

    if (bookService.updateBook(id, bookRequest)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  public ResponseEntity<BookOutputDto> fallbackGetBookByName(String name, Throwable throwable) {
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

  public ResponseEntity<BookOutputDto> fallbackGetBookByIsbn(String isbn, Throwable throwable) {
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
}
