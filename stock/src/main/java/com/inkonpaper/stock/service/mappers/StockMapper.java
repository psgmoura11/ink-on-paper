package com.inkonpaper.stock.service.mappers;

import com.inkonpaper.stock.service.controllers.dtos.StockOutputDto;
import com.inkonpaper.stock.service.domain.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {

  StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

  StockOutputDto stockToStockDto(Stock stock);
}