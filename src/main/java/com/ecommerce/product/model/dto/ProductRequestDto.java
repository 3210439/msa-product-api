package com.ecommerce.product.model.dto;

import com.ecommerce.product.model.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequestDto {
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Price is required")
  private String price;

  @NotNull(message = "Stock is required")
  private Integer stock;

  public Product toEntity() {
    return Product.builder()
        .name(this.name)
        .price(this.price)
        .stock(this.stock)
        .build();
  }
}