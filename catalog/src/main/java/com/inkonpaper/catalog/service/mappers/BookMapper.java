package com.inkonpaper.catalog.service.mappers;

import com.inkonpaper.catalog.service.controllers.dtos.BookOutputDto;
import com.inkonpaper.catalog.service.domain.entities.Author;
import com.inkonpaper.catalog.service.domain.entities.Book;
import com.inkonpaper.catalog.service.domain.entities.Genre;
import com.inkonpaper.catalog.service.domain.entities.Language;
import com.inkonpaper.catalog.service.domain.entities.Publisher;
import com.inkonpaper.catalog.service.domain.entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  @Mapping(source = "publisher", target = "publisher", qualifiedByName = "publisherName")
  @Mapping(source = "authors", target = "authors", qualifiedByName = "authorName")
  @Mapping(source = "languages", target = "languages", qualifiedByName = "languages")
  @Mapping(source = "genres", target = "genres", qualifiedByName = "genres")
  @Mapping(source = "tags", target = "tags", qualifiedByName = "tags")
  BookOutputDto bookToBookDto(Book book);

  List<BookOutputDto> booksToBookDtos(List<Book> books);

  @Named("publisherName")
  default String publisherName(Publisher publisher) {
    return publisher.getPublisherName();
  }

  @Named("authorName")
  default List<String> authorName(Set<Author> authors) {
    return authors.stream()
        .map(Author::getName)
        .collect(Collectors.toList());
  }

  @Named("languages")
  default List<String> languages(Set<Language> languages) {
    return languages.stream()
        .map(Language::getLanguageName)
        .collect(Collectors.toList());
  }

  @Named("genres")
  default List<String> genres(Set<Genre> genres) {
    return genres.stream()
        .map(Genre::getGenreName)
        .collect(Collectors.toList());
  }

  @Named("tags")
  default List<String> tagName(Set<Tag> tags) {
    return tags.stream()
        .map(Tag::getTagName)
        .collect(Collectors.toList());
  }
}
