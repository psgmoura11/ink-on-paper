package com.inkonpaper.stock.service.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Stock {

  @Id
  private Long bookId;

  private int stockAvailable;

  private LocalDateTime dateCreated;

  private LocalDateTime dateUpdated;

  public Stock() {
  }
}