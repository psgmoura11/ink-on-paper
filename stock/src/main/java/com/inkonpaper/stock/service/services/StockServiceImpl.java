package com.inkonpaper.stock.service.services;

import com.inkonpaper.stock.service.domain.entities.Stock;
import com.inkonpaper.stock.service.domain.repositories.StockRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

  private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
  private final StockRepository stockRepository;

  @Autowired
  public StockServiceImpl(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @Override
  public Stock createStock(Long bookId, int stockAvailable) {
    Stock stock = new Stock();
    stock.setBookId(bookId);
    stock.setStockAvailable(stockAvailable);
    LocalDateTime now = LocalDateTime.now();
    stock.setDateCreated(now);
    stock.setDateUpdated(now);
    return stockRepository.save(stock);
  }

  @Override
  public Optional<Stock> updateStock(Long bookId, int newStockAvailable) {

    int updatedRows = stockRepository.updateStock(bookId, newStockAvailable, LocalDateTime.now());

    if (updatedRows > 0) {
      return stockRepository.findById(bookId);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Stock> getStock(Long bookId) {
    return stockRepository.findById(bookId);
  }

  @Override
  public boolean deleteStock(Long bookId) {
    try {
      stockRepository.deleteById(bookId);
      return true;
    } catch (Exception ex) {
      logger.error("Error deleting book with id: {}: {}", bookId, ex.getMessage());
      return false;
    }
  }
}
