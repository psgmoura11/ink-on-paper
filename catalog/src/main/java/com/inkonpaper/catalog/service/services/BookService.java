package com.inkonpaper.catalog.service.services;

import com.inkonpaper.catalog.service.controllers.dtos.BookRequestInputDto;
import com.inkonpaper.catalog.service.domain.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

  List<Book> getBooks();

  Optional<Book> getBookByName(String name);

  Optional<Book> getBookByIsbn(String isbn);

  void createBook(BookRequestInputDto bookRequest) throws Exception;

  boolean deleteBook(Long id);

  boolean updateBook(Long id, BookRequestInputDto bookRequest);

  boolean updateStock(Long id, int stock);
}
