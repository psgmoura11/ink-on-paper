package com.inkonpaper.catalog.service.consumers;

import com.inkonpaper.catalog.service.controllers.dtos.StockMessageDto;
import com.inkonpaper.catalog.service.services.BookService;
import com.inkonpaper.catalog.service.services.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQConsumer {

  private final BookService bookService;

  @Autowired
  public RabbitMQConsumer(BookServiceImpl bookService) {
    this.bookService = bookService;
  }

  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(StockMessageDto stockMessageDto) {
    log.info(String.format("Received stockMessageDto -> %s", stockMessageDto.toString()));
    bookService.updateStock(stockMessageDto.getBookId(), stockMessageDto.getStockAvailable());
  }
}
