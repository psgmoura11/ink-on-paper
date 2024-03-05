package com.inkonpaper.stock.service.services;

import com.inkonpaper.stock.service.domain.entities.Stock;
import java.util.Optional;

public interface StockService {

  Stock createStock(Long bookId, int stockAvailable);

  Optional<Stock> updateStock(Long bookId, int newStockAvailable);

  Optional<Stock> getStock(Long bookId);

  boolean deleteStock(Long bookId);
}