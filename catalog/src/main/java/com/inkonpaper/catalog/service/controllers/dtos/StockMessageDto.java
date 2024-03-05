package com.inkonpaper.catalog.service.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class StockMessageDto {

  private Long bookId;
  private int stockAvailable;
  private LocalDateTime dateCreated;
  private LocalDateTime dateUpdated;
}
