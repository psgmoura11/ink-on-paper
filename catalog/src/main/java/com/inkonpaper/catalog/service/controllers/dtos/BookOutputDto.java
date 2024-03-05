package com.inkonpaper.catalog.service.controllers.dtos;

import java.util.List;
import lombok.Data;

@Data
public class BookOutputDto {

  private Long id;

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

  private String publisher;

  private List<String> authors;

  private List<String> languages;

  private List<String> genres;

  private List<String> tags;
}
