package com.inkonpaper.stock.service.controllers.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StockOutputDto {

  private Long bookId;
  private int stockAvailable;
  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;
}
