package com.ecommerce.product.model.dto;

import com.ecommerce.product.model.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponseDto {
  private Long id;
  private String name;
  private String price;
  private Integer stock;

  public static ProductResponseDto fromEntity(Product product) {
    return ProductResponseDto.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .stock(product.getStock())
        .build();
  }
}