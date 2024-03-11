package com.inkonpaper.catalog.service.services;

import com.inkonpaper.catalog.service.controllers.dtos.BookRequestInputDto;
import com.inkonpaper.catalog.service.domain.entities.Author;
import com.inkonpaper.catalog.service.domain.entities.Availability;
import com.inkonpaper.catalog.service.domain.entities.Book;
import com.inkonpaper.catalog.service.domain.entities.Format;
import com.inkonpaper.catalog.service.domain.entities.Genre;
import com.inkonpaper.catalog.service.domain.entities.Language;
import com.inkonpaper.catalog.service.domain.entities.Publisher;
import com.inkonpaper.catalog.service.domain.entities.Tag;
import com.inkonpaper.catalog.service.domain.repositories.BookRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final RestTemplate restTemplate;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository, RestTemplate restTemplate) {
    this.bookRepository = bookRepository;
    this.restTemplate = restTemplate;
  }

  public List<Book> getBooks() {
    var books = bookRepository.findAll();
    log.debug("books", books);
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
      Book book = mapToBookEntity(bookRequest);

      var persistedBook = bookRepository.save(book);

      createStockForBook(persistedBook.getId(), bookRequest.getStockAvailable());

    } catch (Exception ex) {
      log.error("Error creating book: {}", ex.getMessage());
    }
  }

  public boolean deleteBook(Long id) {
    try {
      bookRepository.deleteById(id);
      return true;

    } catch (Exception ex) {
      log.error("Error deleting book with id {}: {}", id, ex.getMessage());
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
      log.error("Book with id {} not found", id);
      return false;
    }
  }

  public boolean updateStock(Long id, int stock) {
    try {
      bookRepository.updateStock(stock, id);
      return true;
    } catch (Exception ex) {
      log.error("Error updating book with id {}: {}", id, ex.getMessage());
      return false;
    }
  }

  private Book mapToBookEntity(BookRequestInputDto bookRequest) {
    Book book = new Book();
    book.setOriginalTitle(bookRequest.getOriginalTitle());
    book.setIsbn(bookRequest.getIsbn());
    book.setFormat(Format.valueOf(bookRequest.getFormat().toUpperCase()));
    book.setReleaseDate(bookRequest.getReleaseDate());
    book.setEditionDate(bookRequest.getEditionDate());
    book.setEdition(bookRequest.getEdition());
    book.setSeries(bookRequest.isSeries());
    book.setSynopsis(bookRequest.getSynopsis());
    book.setPrice(bookRequest.getPrice());
    book.setPromotionalPrice(bookRequest.getPromotionalPrice());
    book.setAvailability(Availability.valueOf(bookRequest.getAvailability().toUpperCase()));
    book.setStockAvailable(bookRequest.getStockAvailable());

    Publisher publisher = new Publisher();
    publisher.setId(bookRequest.getPublisherId());
    book.setPublisher(publisher);

    Set<Author> authors = new HashSet<>();
    for (Long authorId : bookRequest.getAuthorIds()) {
      Author author = new Author();
      author.setId(authorId);
      authors.add(author);
    }
    book.setAuthors(authors);

    Set<Language> languages = new HashSet<>();
    for (Long languageId : bookRequest.getLanguageIds()) {
      Language language = new Language();
      language.setId(languageId);
      languages.add(language);
    }
    book.setLanguages(languages);

    Set<Genre> genres = new HashSet<>();
    for (Long genreId : bookRequest.getGenreIds()) {
      Genre genre = new Genre();
      genre.setId(genreId);
      genres.add(genre);
    }
    book.setGenres(genres);

    Set<Tag> tags = new HashSet<>();
    for (Long tagId : bookRequest.getTagIds()) {
      Tag tag = new Tag();
      tag.setId(tagId);
      tags.add(tag);
    }
    book.setTags(tags);

    return book;
  }

  private void createStockForBook(Long bookId, int stockAvailable) {

    try {
      String stockServiceUrl = "http://localhost:8081/stock?bookId={bookId}&stockAvailable={stockAvailable}";
      restTemplate.postForEntity(stockServiceUrl, null, Void.class, bookId, stockAvailable);

    } catch (Exception ex) {
      log.error("Error creating stock for book: {}", ex.getMessage());
    }
  }
}
