package com.inkonpaper.catalog.service.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String fullOriginalName;

  private String bornDate;

  private String bornPlace;

  private String diedDate;

  private String diedPlace;

  private String aboutText;

  @ManyToMany(mappedBy = "authors")
  private Set<Book> books;
}
