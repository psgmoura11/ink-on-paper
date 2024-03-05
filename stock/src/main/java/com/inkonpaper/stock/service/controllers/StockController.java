package com.inkonpaper.stock.service.controllers;

import com.inkonpaper.stock.service.controllers.dtos.StockOutputDto;
import com.inkonpaper.stock.service.domain.entities.Stock;
import com.inkonpaper.stock.service.mappers.StockMapper;
import com.inkonpaper.stock.service.publishers.RabbitMQProducer;
import com.inkonpaper.stock.service.services.StockService;
import com.inkonpaper.stock.service.services.StockServiceImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stock")
public class StockController {

  private final RabbitMQProducer producer;
  private final StockService stockService;

  @Autowired
  public StockController(StockServiceImpl stockService, RabbitMQProducer producer) {
    this.stockService = stockService;
    this.producer = producer;
  }

  @PostMapping
  public ResponseEntity<Void> createStock(@RequestParam Long bookId,
      @RequestParam int stockAvailable) {

    stockService.createStock(bookId, stockAvailable);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<StockOutputDto> getStock(@PathVariable Long bookId) {

    Optional<Stock> stock = stockService.getStock(bookId);

    if (stock.isPresent()) {

      var response = StockMapper.INSTANCE.stockToStockDto(stock.get());
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{bookId}")
  public ResponseEntity<Void> updateStock(@PathVariable Long bookId,
      @RequestParam int newStockAvailable) {

    Optional<Stock> updatedStock = stockService.updateStock(bookId, newStockAvailable);

    if (updatedStock.isPresent()) {

      var response = StockMapper.INSTANCE.stockToStockDto(updatedStock.get());
      producer.sendMessage(response);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> deleteStock(@PathVariable Long bookId) {

    boolean result = stockService.deleteStock(bookId);
    return result ?
        ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
