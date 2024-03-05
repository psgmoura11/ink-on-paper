package com.inkonpaper.catalog.service.services;

import com.inkonpaper.catalog.service.controllers.dtos.BookRequestInputDto;
import com.inkonpaper.catalog.service.domain.entities.Book;
import com.inkonpaper.catalog.service.domain.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

  private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> getBooks() {
    var books = bookRepository.findAll();
    logger.debug("books", books);
    return books;
  }

  public Optional<Book> getBookByName(String name) {
    return bookRepository.findByOriginalTitle(name);
  }

  public Optional<Book> getBookByIsbn(String isbn) {
    return bookRepository.findByIsbn(isbn);
  }

  public void createBook(BookRequestInputDto bookRequest) {
    try {
      Book book = new Book();
      book.setOriginalTitle(bookRequest.getOriginalTitle());
      book.setIsbn(bookRequest.getIsbn());
      // Set other properties similarly
      // Remember to handle relationships like authors, genres, etc.

      bookRepository.save(book);
    } catch (Exception ex) {
      logger.error("Error creating book: {}", ex.getMessage());
    }
  }

  public boolean deleteBook(Long id) {
    try {
      bookRepository.deleteById(id);
      return true;
    } catch (Exception ex) {
      logger.error("Error deleting book with id {}: {}", id, ex.getMessage());
      return false;
    }
  }

  public boolean updateBook(Long id, BookRequestInputDto bookRequest) {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      book.setOriginalTitle(bookRequest.getOriginalTitle());
      book.setIsbn(bookRequest.getIsbn());

      bookRepository.save(book);
      return true;
    } else {
      logger.error("Book with id {} not found", id);
      return false;
    }
  }

  public boolean updateStock(Long id, int stock) {
    try {
      bookRepository.updateStock(stock, id);
      return true;
    } catch (Exception ex) {
      logger.error("Error updating book with id {}: {}", id, ex.getMessage());
      return false;
    }
  }

}
