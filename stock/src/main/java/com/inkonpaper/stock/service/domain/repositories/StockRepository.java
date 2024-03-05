package com.inkonpaper.stock.service.domain.repositories;

import com.inkonpaper.stock.service.domain.entities.Stock;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StockRepository extends JpaRepository<Stock, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE Stock SET stockAvailable = :newStockAvailable, dateUpdated = :dateUpdated WHERE id = :bookId")
  int updateStock(@Param("bookId") Long bookId, @Param("newStockAvailable") int newStockAvailable,
      @Param("dateUpdated") LocalDateTime dateUpdated);
}
