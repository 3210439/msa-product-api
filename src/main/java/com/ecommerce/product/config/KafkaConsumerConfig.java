package com.ecommerce.product.config;

import com.ecommerce.product.model.OrderEvent;
import com.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

  @Autowired
  private ProductService productService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "order-event", groupId = "product-api")
  public void listenOrderEvent(String message) {
    try {
      OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
      log.info("Received order event: {}", event);
      productService.updateStock(event.getProductId(), event.getQuantity());
      log.info("Stock updated for productId: {}", event.getProductId());
    } catch (Exception e) {
      log.error("Error processing order event: {}", message, e);
      throw new RuntimeException("Failed to process order event", e);
    }
  }

}
