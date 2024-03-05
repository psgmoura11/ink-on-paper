package com.inkonpaper.catalog.service.controllers.dtos;

import java.util.Set;
import lombok.Data;

@Data
public class BookRequestInputDto {

  private String originalTitle;
  private String isbn;
  private String format;
  private String releaseDate;
  private String editionDate;
  private int edition;
  private boolean series;
  private String synopsis;
  private double price;
  private Double promotionalPrice;
  private String availability;
  private int stockAvailable;
  private Long publisherId;
  private Set<Long> authorIds;
  private Set<Long> languageIds;
  private Set<Long> genreIds;
  private Set<Long> tagIds;

  public BookRequestInputDto() {
  }

  public BookRequestInputDto(String originalTitle, String isbn, String format, String releaseDate,
      String editionDate,
      int edition, boolean series, String synopsis, double price, Double promotionalPrice,
      String availability, int stockAvailable, Long publisherId, Set<Long> authorIds,
      Set<Long> languageIds, Set<Long> genreIds, Set<Long> tagIds) {
    this.originalTitle = originalTitle;
    this.isbn = isbn;
    this.format = format;
    this.releaseDate = releaseDate;
    this.editionDate = editionDate;
    this.edition = edition;
    this.series = series;
    this.synopsis = synopsis;
    this.price = price;
    this.promotionalPrice = promotionalPrice;
    this.availability = availability;
    this.stockAvailable = stockAvailable;
    this.publisherId = publisherId;
    this.authorIds = authorIds;
    this.languageIds = languageIds;
    this.genreIds = genreIds;
    this.tagIds = tagIds;
  }
}
