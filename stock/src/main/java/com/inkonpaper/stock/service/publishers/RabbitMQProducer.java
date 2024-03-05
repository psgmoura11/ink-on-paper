package com.inkonpaper.stock.service.publishers;

import com.inkonpaper.stock.service.controllers.dtos.StockOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQProducer {

  private final RabbitTemplate rabbitTemplate;
  @Value("${rabbitmq.exchange.name}")
  private String exchange;
  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(StockOutputDto stockOutputDto) {
    log.info(String.format("Message sent -> %s", stockOutputDto.toString()));
    rabbitTemplate.convertAndSend(exchange, routingKey, stockOutputDto);
  }
}
