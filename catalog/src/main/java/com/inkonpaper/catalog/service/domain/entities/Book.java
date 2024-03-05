package com.inkonpaper.catalog.service.domain.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalTitle;

  private String isbn;

  @Enumerated(EnumType.STRING)
  private Format format;

  private String releaseDate;

  private String editionDate;

  private int edition;

  private boolean series;

  private String synopsis;

  private double price;

  @Nullable
  private Double promotionalPrice;

  @Enumerated(EnumType.STRING)
  private Availability availability;

  private int stockAvailable;

  @ManyToOne
  @JoinColumn(name = "publisher_id")
  private Publisher publisher;

  @ManyToMany
  @JoinTable(name = "book_author",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors;

  @ManyToMany
  @JoinTable(name = "book_language",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "language_id"))
  private Set<Language> languages;

  @ManyToMany
  @JoinTable(name = "book_genre",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres;

  @ManyToMany
  @JoinTable(name = "book_tag",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;

  public Book() {
  }
}
