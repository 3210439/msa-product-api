package com.ecommerce.product.model;

import lombok.Data;

@Data
public class OrderEvent {
  private String eventId;
  private Long orderId;
  private String userId;
  private String status;
  private Long productId;
  private Integer quantity;
}
